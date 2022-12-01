import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Group {
    private static final String PATH = "..\\config\\", FNAME = "victims.txt";
    private ArrayList<Victim> targets;

    List<Integer> randomNumbers = new ArrayList<>();
    private Victim sender;
    private int size;

    private static final Random RAND = new Random();

    Group(int size) {
        //TODO
        if (size < 3) {
            //nonono can't
        }

        targets = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get("C:\\Users\\timot\\Documents\\HEIG\\DAI\\Labos" +
                            "\\DAI-2022-SMTP\\config\\victims.txt"));
            initRandomNumbers(lines.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Add the first victim
        for (int i = 0; i < size - 1; ++i) {
            targets.add(new Victim(lines.get(randomNumbers.get(i))));
        }
        sender = new Victim(lines.get(size));
    }

    /**
     * Create a list onf unique random numbers between 1 - n
     * @param n the upper bound of the range 1 - n
     */
    private void initRandomNumbers(int n) {
        for (int i = 1; i <= n; i++) {
            randomNumbers.add(i);
        }
        Collections.shuffle(randomNumbers);
    }
}
