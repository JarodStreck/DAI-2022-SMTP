package ch.heig.dai.mail;

import java.util.ArrayList;
import java.util.List;

public class Mail {
    private final Victim sender;
    private final List<Victim> recipients;
    private final Message message;

    public Mail(Message message, Victim sender, List<Victim> recipients) {
        this.message = message;
        this.sender = sender;
        this.recipients = recipients;
    }

    public String getSubject() {
        return message.getSubject();
    }

    public String getContent(){
        return message.getContent();
    }

    public String getSenderMail() {
        return sender.getEmail();
    }

    public List<String> getRecipientsMail(){
        List<String> emails = new ArrayList<>();
        for(Victim r : recipients)
            emails.add(r.getEmail());
        return emails;
    }
}
