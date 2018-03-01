package ru.fom.mail.services;

import ru.fom.mail.MailboxService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.fom.mail.dictionaries.Provider.YANDEX;
import static ru.fom.mail.rest.YandexAPI.retrofit;

public class YandexMailboxService implements MailboxService {

    private final String token, domain;
    private String email, pass;

    public YandexMailboxService(String token, String domain) {
        this.token = token;
        this.domain = domain;
    }

    public YandexMailboxService logIn(String email, String pass) {
        this.email = email;
        this.pass = pass;
        return this;
    }

    public YandexMailboxService createMailbox() {
        return createMailbox(email, pass);
    }

    public YandexMailboxService createMailbox(String email, String pass) {
        final Map<String,String> payload = new HashMap<String, String>();
        payload.put("domain", domain);
        payload.put("login", email);
        payload.put("password", pass);
        try {
            retrofit.createMailbox(token, payload).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public YandexMailboxService updateMailbox(String newPassword, String firstName, String lastName,
                                              boolean activate, String birthDate, Integer gender, String hintQuestion,
                                              String hintAnswer) {
        final Map<String,String> payload = new HashMap<String, String>();
        payload.put("domain", domain);
        payload.put("login", email);
        payload.put("password", newPassword.isEmpty() ? pass : newPassword);
        payload.put("iname", firstName);
        payload.put("fname", lastName);
        payload.put("enabled", activate ? "yes" : "no");
        payload.put("birth_date", birthDate);
        payload.put("sex", gender.toString());
        payload.put("hintq", hintQuestion);
        payload.put("hinta", hintAnswer);
        try {
            retrofit.editMailbox(token, payload).execute();
            if (!newPassword.isEmpty()) {
                this.pass = newPassword;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public void deleteMailbox(String email) {
        try {
            retrofit.deleteMailbox(token, domain, email).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getMailboxes(String page, String quantityOnPage) {
        try {
            return (List<T>) retrofit.getAllMailboxes(token, domain, page, quantityOnPage).execute().body().getAccounts();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public MailService openMailbox() {
        return new MailService(YANDEX, email, pass);
    }


}
