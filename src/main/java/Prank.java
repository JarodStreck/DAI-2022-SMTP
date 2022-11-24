import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

public class Prank {
   private Group[] groups;
   private String victimFile;
   private String messageFile;

    Prank(int numberOfGroup,String victimFile,String messageFile) throws IOException {
        groups = new Group[numberOfGroup];
        this.victimFile = victimFile;
        this.messageFile = messageFile;
        Vector<Victim> victims = readVictimsFromFile(victimFile);
        int size = victims.size() / numberOfGroup;
        this.groups = generateGroups(victims,size);
    }

    private Vector<Victim> readVictimsFromFile(String filename) throws IOException {
        //Lis le fichier victime.txt et les transform en objec victim
        BufferedReader input = null;
        Vector<Victim> victims = new Vector<Victim>();
        int nbVictims = 0 ;

        try{
            input = new BufferedReader(new FileReader(filename, StandardCharsets.UTF_8));

        }catch (IOException e){
            e.printStackTrace();
        }
        String email;
        while((email = input.readLine()) != null){
            victims.add(new Victim(email));
        };

        return victims;
    }
    private Group[] generateGroups(Vector<Victim> victims,int size){
        //Va Générér les groups selon les victim lu dans le fichier
     return new Group[0];
    }
    public void sendMail(){
        //Envoie un mail (connexion client smtp + envoie)
    }
}
