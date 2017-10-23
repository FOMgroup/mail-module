package ru.fom.mail.dictionaries;

public enum Provider {

    YANDEX("imap.yandex.ru"),
    GMAIL("imap.gmail.com"),
    MAIL_RU("imap.mail.ru");

    private final String value;

    Provider(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
