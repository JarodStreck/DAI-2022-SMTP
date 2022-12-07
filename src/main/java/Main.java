import ch.heig.dai.config.ConfigLoader;
import ch.heig.dai.mail.Group;
import ch.heig.dai.mail.Victim;
import ch.heig.dai.smtp.Client;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConfigLoader cl = new ConfigLoader();
        List<Victim> victims = cl.getVictims();

        Client client = new Client();
        client.connect(cl.getServerAddress(), cl.getServerPort());

        //sending n mails with groups of victims
        for(int i = 0; i < cl.getNumberOfGroups(); i++){
            Group group = new Group(victims.size()/cl.getNumberOfGroups(), victims);
            client.send(group.generate(cl.getMessages()));
        }
        client.close();
    }
}