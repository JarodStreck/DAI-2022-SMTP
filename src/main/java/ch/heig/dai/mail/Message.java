package ch.heig.dai.mail;

/**
 * Represents a message with a subject and a content
 *
 * @author Jarod Streckeisen, Timothee Van Hove
 */
public class Message {
    private final String subject;
    private final String content;

    public Message(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    /**
     * Get the subject of a message
     * @return a string containing the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Get the content of the message
     * @return a string containing the message
     */
    public String getContent() {
        return content;
    }
}
