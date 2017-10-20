package ru.fom.mail.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.mail.Message;
import javax.mail.internet.MimeMultipart;

public class MailParsingService {

    public Document parseEmail(Message message) {
        Document result = null;
        try {
            if (message.getContentType().contains("multipart")) {
                MimeMultipart multipart = (MimeMultipart) message.getContent();
                result = Jsoup.parse(multipart.getBodyPart(1).getContent().toString());
            } else {
                result = Jsoup.parse(message.getContent().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
