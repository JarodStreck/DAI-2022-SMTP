package ch.heig.dai.mail;

import java.util.*;

public class Group {
    private final ArrayList<Victim> recipients;
    private final Victim sender;

    public Group(int size, List<Victim> victims) {
        if (size < 3)
            throw new RuntimeException("A group must contain al least 3 victims");

        if(victims.size() < size)
            throw new RuntimeException("The given list does not contains enough victims");

        //Copy the given list
        List<Victim> temp = new ArrayList<>(victims);

        //Shuffle it
        Collections.shuffle(temp);

        //The sender is the first victim from the list
        sender = temp.get(0);

        //The victims are from 1 to size+1 from the list
        recipients = new ArrayList<>(temp.subList(1, size));
    }

    public List<Victim> getRecipients(){
        return recipients;
    }
    public Victim getSender(){
        return sender;
    }
}
