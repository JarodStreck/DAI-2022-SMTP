package ch.heig.dai.mail;

import java.util.Collections;
import java.util.List;

public class MailGenerator {
    private final List<Message> messages;

    public MailGenerator(List<Message> messages) {
        this.messages = messages;
    }

    public Mail generate(Group group){
        Collections.shuffle(messages);
        return new Mail(messages.get(0), group.getSender(), group.getRecipients());
    }
}
