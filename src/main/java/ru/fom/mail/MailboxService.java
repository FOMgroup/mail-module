package ru.fom.mail;

import ru.fom.mail.services.MailService;

import java.util.List;

public interface MailboxService {

    MailboxService logIn(String email, String pass);

    MailboxService createMailbox();

    MailboxService createMailbox(String email, String pass);

    MailboxService updateMailbox(String newPassword, String firstName, String lastName,
                                 boolean activate, String birthDate, Integer gender, String hintQuestion,
                                 String hintAnswer);

    void deleteMailbox(String email);

    <T> List<T> getMailboxes(String page, String quantityOnPage);

    MailService openMailbox();

}
