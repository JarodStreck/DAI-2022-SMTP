import ch.heig.dai.config.ConfigLoader;
import ch.heig.dai.mail.Group;
import ch.heig.dai.mail.Mail;
import ch.heig.dai.mail.MailGenerator;
import ch.heig.dai.mail.Victim;
import ch.heig.dai.smtp.Client;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        //ch.heig.dai.mail.Group group = new ch.heig.dai.mail.Group(5);
        ConfigLoader cl = new ConfigLoader();
        MailGenerator generator = new MailGenerator(cl.getMessages());
        List<Victim> victims = cl.getVictims();
        Client client = new Client();
        client.connect(cl.getServerAddress(), cl.getServerPort());

        //sending n mails with groups of 4 victims
        for(int i = 0; i < cl.getNumberOfGroups(); i++){
            client.send(generator.generate(new Group(4, victims)));
        }
        client.close();
    }
}