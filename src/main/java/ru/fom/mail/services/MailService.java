package ru.fom.mail.services;

import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import org.jsoup.nodes.Document;
import ru.fom.mail.dictionaries.Provider;

import javax.mail.*;
import javax.mail.search.AndTerm;
import javax.mail.search.BodyTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;
import static javax.mail.Folder.READ_ONLY;
import static ru.fom.mail.services.MailParsingService.parseEmail;

public class MailService {

    private static final String protocol = "imaps";
    private static final int maxAttempts = System.getProperty("mail.max_attempts") != null ?
            Integer.parseInt(System.getProperty("mail.max_attempts")) : 10;

    private static final RetryPolicy retryPolicy = new RetryPolicy()
            .retryWhen(null)
            .retryOn(NullPointerException.class)
            .retryOn(AuthenticationFailedException.class)
            .withDelay(6, SECONDS)
            .withMaxRetries(maxAttempts);

    private String provider, userEmail, userPass;

    public MailService(Provider provider, String userEmail, String userPass) {
        this.provider = provider.toString();
        this.userEmail = userEmail;
        this.userPass = userPass;
    }

    public List<Document> getAllEmails() {
        return Failsafe.with(retryPolicy)
                .get(()-> getAllRawEmails().stream().map(MailParsingService::parseEmail).collect(Collectors.toList()));
    }

    public List<Message> getAllRawEmails() {
        return getAllRawEmails("Inbox");
    }

    public List<Document> getAllEmails(String folderName) {
        return Failsafe.with(retryPolicy)
                .get(()-> getAllRawEmails(folderName).stream().map(MailParsingService::parseEmail).collect(Collectors.toList()));
    }

    public List<Message> getAllRawEmails(String folderName) {
        final Session session = Session.getInstance(System.getProperties(), null);
        try {
            final Store store = session.getStore(protocol);
            store.connect(provider, userEmail, userPass);
            final Folder inbox = store.getFolder(folderName);
            inbox.open(READ_ONLY);
            return inbox.getMessages().length > 0 ? Arrays.asList(inbox.getMessages()) : null;
        } catch (Exception e) {
            return null;
        }
    }

    public Document getEmailBySubject(String subject) {
        return Failsafe.with(retryPolicy).get(()-> parseEmail(getRawEmailBySubject(subject)));
    }

    public Message getRawEmailBySubject(String subject) {
        return getRawEmail(new SubjectTerm(subject));
    }

    public Document getEmailBySubject(String folderName, String subject) {
        return Failsafe.with(retryPolicy).get(()-> parseEmail(getRawEmailBySubject(folderName, subject)));
    }

    public Message getRawEmailBySubject(String folderName, String subject) {
        return getRawEmail(folderName, new SubjectTerm(subject));
    }

    public Document getEmailByKeyword(String keyword) {
        return Failsafe.with(retryPolicy).get(()-> parseEmail(getRawEmailByKeyword(keyword)));
    }

    public Message getRawEmailByKeyword(String keyword) {
        BodyTerm[] terms = Arrays.stream(
                keyword.replace(".", "")
                        .replace("!", "")
                        .replace(",", "")
                        .replace(":", "")
                        .replace("-", " ")
                        .replace("(", "")
                        .replace(")", "")
                        .split(" "))
                .map(BodyTerm::new).collect(Collectors.toList()).toArray(new BodyTerm[]{});
        return getRawEmail(new AndTerm(terms));
    }

    public Document getEmailByKeyword(String folderName, String keyword) {
        return Failsafe.with(retryPolicy).get(()-> parseEmail(getRawEmailByKeyword(folderName, keyword)));
    }

    public Message getRawEmailByKeyword(String folderName, String keyword) {
        return getRawEmail(folderName, new BodyTerm(keyword));
    }

    public Document getEmailBySubjectWithKeyword(String subject, String keyword) {
        return Failsafe.with(retryPolicy).get(()-> parseEmail(getRawEmailBySubjectWithKeyword(subject, keyword)));
    }

    public Message getRawEmailBySubjectWithKeyword(String subject, String keyword) {
        return getRawEmailBySubjectWithKeyword("Inbox", subject, keyword);
    }

    public Document getEmailBySubjectWithKeyword(String folderName, String subject, String keyword) {
        return Failsafe.with(retryPolicy).get(()-> parseEmail(getRawEmailBySubjectWithKeyword(folderName, subject, keyword)));
    }

    public Message getRawEmailBySubjectWithKeyword(String folder, String subject, String keyword) {
        BodyTerm[] terms = Arrays.stream(
                keyword.replace(".", "")
                        .replace("!", "")
                        .replace(",", "")
                        .replace(":", "")
                        .replace("-", " ")
                        .replace("(", "")
                        .replace(")", "")
                        .split(" "))
                .map(BodyTerm::new).collect(Collectors.toList()).toArray(new BodyTerm[]{});
        return getRawEmail(folder, new AndTerm(new SubjectTerm(subject), new AndTerm(terms)));
    }

    private Message getRawEmail(SearchTerm criteria) {
        return getRawEmail("Inbox", criteria);
    }

    private Message getRawEmail(String folderName, SearchTerm criteria) {
        final Session session = Session.getInstance(System.getProperties(), null);
        Message result = null;
        try {
            final Store store = session.getStore(protocol);
            store.connect(provider, userEmail, userPass);
            final Folder inbox = store.getFolder(folderName);
            inbox.open(READ_ONLY);
            final Message[] found = inbox.search(criteria);
            result = found.length > 0 ? found[0] : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
