package ch.heig.dai.mail;

import java.util.*;

/**
 * Handles the recipients, the sender of the mail and the mail generation
 *
 * @author Jarod Streckeisen, Timothee Van Hove
 */
public class Group {
    private static int groupId = 0;
    private final ArrayList<Victim> recipients;
    private final Victim sender;

    public Group(int size, List<Victim> victims) {
        if (size < 3)
            throw new RuntimeException("A group must contain al least 3 victims");

        if (victims.size() < size)
            throw new RuntimeException("The given list does not contains enough victims");

        //Calculate the offset for the mail selection
        int offset = size * groupId;

        //Copy the given list
        List<Victim> temp = new ArrayList<>(victims);

        //The sender is the first victim from the list
        sender = temp.get(offset);

        //The victims are from 1 to size+1 from the list
        recipients = new ArrayList<>(temp.subList(1 + offset, size + offset));
        groupId++;
    }

    /**
     * Generate an email for a group with a randomly selected message
     *
     * @param messages a list of potential message that can be sent.
     * @return a Mail ready to be used by the SMTP client
     */
    public Mail generate(List<Message> messages) {
        Collections.shuffle(messages);
        return new Mail(messages.get(0), sender, recipients);
    }
}
