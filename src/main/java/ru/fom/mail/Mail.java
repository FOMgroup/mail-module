package ru.fom.mail;

import ru.fom.mail.dictionaries.Provider;
import ru.fom.mail.services.YandexMailboxService;

public class Mail {

    private static final String yandexToken = System.getProperty("yandex.token");
    private static final String yandexDomain = System.getProperty("yandex.domain");

    public static MailboxService getMailboxService(Provider provider) {
        switch (provider) {
            case YANDEX:
                return new YandexMailboxService(yandexToken, yandexDomain);

            default:
                return new YandexMailboxService(yandexToken, yandexDomain);
        }

    }

}
