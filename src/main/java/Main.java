import ch.heig.dai.config.ConfigLoader;
import ch.heig.dai.mail.Group;
import ch.heig.dai.mail.Victim;
import ch.heig.dai.smtp.Client;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        //Load data from config files
        ConfigLoader loader = new ConfigLoader();
        List<Victim> victims = loader.getVictims();

        //Client connection
        Client client = new Client();
        client.connect(loader.getServerAddress(), loader.getServerPort());

        //sending n mails with groups of victims
        int groupSize = victims.size() / loader.getNumberOfGroups();
        for (int i = 0; i < loader.getNumberOfGroups(); i++) {
            Group group = new Group(groupSize, victims);
            client.send(group.generate(loader.getMessages()));
        }
        client.close();
    }
}