package ch.heig.dai.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a mail with recipients, a sender, and a message
 *
 * @author Jarod Streckeisen, Timothee Van Hove
 */
public class Mail {
    private final Victim sender;
    private final List<Victim> recipients;
    private final Message message;

    public Mail(Message message, Victim sender, List<Victim> recipients) {
        this.message = message;
        this.sender = sender;
        this.recipients = recipients;
    }

    /**
     * Get the subject of the mail
     * @return a string containing the subject
     */
    public String getSubject() {
        return message.getSubject();
    }

    /**
     * Get the content of the body
     * @return a string containing the content
     */
    public String getContent(){
        return message.getContent();
    }

    /**
     * get the email of the sender
     * @return a string representation of the email
     */
    public String getSenderMail() {
        return sender.getEmail();
    }

    /**
     * Get all the recipients of the mail
     * @return a list of string representation of the mail
     */
    public List<String> getRecipientsMail(){
        List<String> emails = new ArrayList<>();
        for(Victim r : recipients)
            emails.add(r.getEmail());
        return emails;
    }

    /**
     * Returns the SMTP content type matching the content of the message content
     * @return The content type as String
     */
    public String getContentType(){
        if(message.getContent().contains("<html>"))
            return "Content-Type: text/html;";
        return "Content-Type: text/plain;";
    }
}
