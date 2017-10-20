package ru.fom.mail.services;

import javax.mail.*;
import javax.mail.search.*;
import java.util.Arrays;
import java.util.List;

import static javax.mail.Folder.READ_WRITE;

public class MailService {

    private static final String protocol = "imaps";
    private String provider, userEmail, userPass;

    public MailService(String provider, String userEmail, String userPass) {
        this.provider = provider;
        this.userEmail = userEmail;
        this.userPass = userPass;
    }

    public List<Message> getAllEmails() {
        return getAllEmails("Inbox");
    }

    public List<Message> getAllEmails(String folderName) {
        final Session session = Session.getInstance(System.getProperties(), null);
        List<Message> result = null;
        try {
            final Store store = session.getStore(protocol);
            store.connect(provider, userEmail, userPass);
            final Folder inbox = store.getFolder(folderName);
            inbox.open(READ_WRITE);
            result = inbox.getMessages().length > 0 ? Arrays.asList(inbox.getMessages()) : null;
            inbox.close(true);
            store.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Message getEmailBySubject(String subject) {
        return getEmail(new SubjectTerm(subject));
    }

    public Message getEmailBySubject(String folderName, String subject) {
        return getEmail(folderName, new SubjectTerm(subject));
    }

    public Message getEmailByKeyword(String keyword) {
        return getEmail(new BodyTerm(keyword));
    }

    public Message getEmailByKeyword(String folderName, String keyword) {
        return getEmail(folderName, new BodyTerm(keyword));
    }

    public Message getEmailBySubjectWithKeyword(String subject, String keyword) {
        return getEmail(new AndTerm(new SubjectTerm(subject), new BodyTerm(keyword)));
    }

    public Message getEmailBySubjectWithKeyword(String folder, String subject, String keyword) {
        return getEmail("Inbox", new AndTerm(new SubjectTerm(subject), new BodyTerm(keyword)));
    }

    public Message getEmail(SearchTerm criteria) {
        return getEmail("Inbox", criteria);
    }

    public Message getEmail(String folderName, SearchTerm criteria) {
        final Session session = Session.getInstance(System.getProperties(), null);
        Message result = null;
        try {
            final Store store = session.getStore(protocol);
            store.connect(provider, userEmail, userPass);
            final Folder inbox = store.getFolder(folderName);
            inbox.open(READ_WRITE);
            final Message[] found = inbox.search(criteria);
            result = found.length > 0 ? found[0] : null;
            inbox.close(true);
            store.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

}
