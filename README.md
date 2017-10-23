# mail-module

Mail module is a library designed to be used in tests automation. It allows to create mailboxes using API's like Yandex PDD.
In future we plan to add Google and Mail.ru APIs support.

The second part of this module is to collect mail from mailboxes created by APIs listed above.

Code example:

``` java
    
    import ru.fom.mail.dictionaries.Provider.YANDEX;
    import static ru.fom.mail.Mail.getMailboxService;
    
    public class Foo {
    
        // Important: Before usage get your Token from https://pdd.yandex.ru/
        static {
            System.setProperty("yandex.token", "Your awesome Token");
            System.setProperty("yandex.domain", "your-domain.tld");
        }
        
        MailboxService service = getMailboxService(YANDEX);
        
        // To create Mailbox
        service.createMailbox("your.box@your-domain.tld", "password");
        // Or
        service.logIn("your.box@your-domain.tld", "password").createMailbox();
        
        /** To update your account. First argument is new pass. Leave blank if you are not planning to change it now.
        * Second argument is the First name and the third is Surname. Fourth argument is boolean active/not active.
        * Fifth argument is DoB and 6th is gender 1 for male and 2 for female. Last 2 argument is recovery info. This
        * method is specific for Yandex PDD
        */
        service.updateMailbox("", "Chuck", "Norris", true, "1960-02-24", 1, "Hint question", "Hint Answer");
        
        // To list your accounts use getMailboxes method. First argument is page No., the second is users per page. 
        List<YandexUser> users = service.getMailboxes(1, 500);
        
        // To delete a mailbox
        service.deleteMailbox("your.box@your-domain.tld");
        
        // To use the mailbox. Don't forget to logIn if you havent done it before
        service.openMailbox();
        
        /** You can also use your mailbox straightaway after creation. After summoning openMailbox() method, 
        * you can get either raw message (Javamail object) or Document (From JSOUP) for easy parsing.
        * In case you are retrieving Document objects you can set System.setProperty("mail.max_attempts", 60) for smart
        * waits untill you get your message with polling each second.
        */
        service.createMailbox("your.box@your-domain.tld", "password").openMailbox().getAllEmails();
    
    }
    
```
