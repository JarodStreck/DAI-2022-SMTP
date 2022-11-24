import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Prank {
   private Group[] groups;
   private String victimFile;
   private String messageFile;

    Prank(int numberOfGroup,String victimFile,String messageFile) throws IOException {
        groups = new Group[numberOfGroup];
        this.victimFile = victimFile;
        this.messageFile = messageFile;
        Victim[] victims = readVictimsFromFile(victimFile);
        int size = victims.length / numberOfGroup;
        this.groups = generateGroups(victims,size);
    }

    private Victim[] readVictimsFromFile(String filename) throws IOException {
        //Lis le fichier victime.txt et les transform en objec victim
        try{
            InputStreamReader input = new FileReader(filename, StandardCharsets.UTF_8);
        }catch (IOException e){
            e.printStackTrace();
        }

        return new Victim[0];
    }
    private Group[] generateGroups(Victim[] victims,int size){
        //Va Générér les groups selon les victim lu dans le fichier
     return new Group[0];
    }
    public void sendMail(){
        //Envoie un mail (connexion client smtp + envoie)
    }
}
