import java.util.Random;
import java.util.Vector;

public class Group {
    private Vector<Victim> recievers;
    private Victim sender;
    private int size;
    Group(int size){
        //TODO
        if(size < 3){
            //nonono can't
        }

    }
    void pickRandomSender(){
        Random r = new Random();
        int index = r.nextInt(recievers.size()-1);
        sender = recievers.elementAt(index);
        recievers.remove(index);
    }
}
