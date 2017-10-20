package ru.fom.mail.services;

import ru.fom.mail.entities.YandexResponse;
import ru.fom.mail.entities.YandexUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.fom.mail.rest.YandexAPI.retrofit;

public class YandexMaiboxService {

    private final String token, domain;

    public YandexMaiboxService(String token, String domain) {
        this.token = token;
        this.domain = domain;
    }

    public void createMailbox(String email, String pass) {
        final Map<String,String> payload = new HashMap<String, String>();
        payload.put("domain", domain);
        payload.put("login", email);
        payload.put("password", pass);
        try {
            retrofit.createMailbox(token, payload).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateMailbox(String email, String newPassword, String firstName, String lastName,
                              boolean activate, String birthDate, Integer gender, String hintQuestion,
                              String hintAnswer) {
        final Map<String,String> payload = new HashMap<String, String>();
        payload.put("domain", domain);
        payload.put("login", email);
        if (!newPassword.isEmpty()) payload.put("password", newPassword);
        payload.put("iname", firstName);
        payload.put("fname", lastName);
        payload.put("enabled", activate ? "yes" : "no");
        payload.put("birth_date", birthDate);
        payload.put("sex", gender.toString());
        payload.put("hintq", hintQuestion);
        payload.put("hinta", hintAnswer);
        try {
            retrofit.editMailbox(token, payload).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteMailbox(String domain, String email) {
        try {
            retrofit.deleteMailbox(token, email).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public YandexResponse getMailboxes(String page, String quantityOnPage) {
        try {
            return retrofit.getAllMailboxes(token, domain, page, quantityOnPage).execute().body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
