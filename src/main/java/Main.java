import ch.heig.dai.config.ConfigLoader;
import ch.heig.dai.mail.Group;
import ch.heig.dai.mail.Mail;
import ch.heig.dai.mail.MailGenerator;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        //ch.heig.dai.mail.Group group = new ch.heig.dai.mail.Group(5);
        ConfigLoader cl = new ConfigLoader();
        List<Group> groups = new ArrayList<>();

        //Creating a list of group of 4 victims
        for(int i = 0; i < cl.getNumberOfGroups(); i++){
            groups.add(new Group(4, cl.getVictims()));
        }

        MailGenerator generator = new MailGenerator(cl.getMessages());

        //Creating a list of mails
        List<Mail> mails = new ArrayList<>();
        for(Group g : groups){
            mails.add(generator.generate(g));
        }



        //Client client = new Client();


        //client.run();

    }
}