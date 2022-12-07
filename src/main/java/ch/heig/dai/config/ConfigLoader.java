package ch.heig.dai.config;


import ch.heig.dai.mail.Message;
import ch.heig.dai.mail.Victim;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles the configuration loading and validation
 *
 * @author Jarod Streckeisen, Timothee Van Hove
 */
public class ConfigLoader {
    private static Pattern MAIL_PATTERN;
    private static final String VICTIMS_PATH = "./config/victims.txt",
            CFG_PATH = "./config/config.properties",
            MSG_PATH = "./config/messages.txt",
            REGEX_PATH = "./config/RFC822_RegexAddressValidation.txt";
    private List<Victim> victims;
    private List<Message> messages;
    private String serverAddress;
    private int serverPort;
    private int numberOfGroups;

    public ConfigLoader() {
        try {
            MAIL_PATTERN = getRegexFromFile();
            victims = getVictimsFromFile();
            messages = getMessagesFromFile();
            getConfigFromFile();
        } catch (IOException e) {
            System.err.println("Unable to load one of the config file correctly");
        } catch (RuntimeException e) {
            System.err.println("The configuration has errors : " + e.getMessage());
        }
    }

    /**
     * Get the victims from the victims config file
     *
     * @return a list of Victim
     * @throws IOException if an error occurred when file is open or read
     */
    private List<Victim> getVictimsFromFile() throws IOException {
        ArrayList<Victim> victims = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(VICTIMS_PATH,
                StandardCharsets.UTF_8));

        String line;
        while ((line = reader.readLine()) != null) {
            if (!isAddressValid(line))
                throw new RuntimeException("The mail address : " + line + " is invalid");

            victims.add(new Victim(line));
        }
        //Checks if the number of retrieve emails is enough
        if (victims.size() < 3)
            throw new RuntimeException("The minimum group size is 3 victims, " +
                    victims.size() + " found.");

        Collections.shuffle(victims);
        return victims;
    }

    /**
     * Get the messages from the messages config file
     *
     * @return a list of Message
     * @throws IOException if an error occurred when file is open or read
     */
    private List<Message> getMessagesFromFile() throws IOException {
        List<Message> messages = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(MSG_PATH,
                StandardCharsets.UTF_8));

        String line;
        StringBuilder content = new StringBuilder(), subject = new StringBuilder();
        boolean ignoreNextBlank = false;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("s:")) {
                subject.append(line.replaceFirst("s:", ""));
                ignoreNextBlank = false;
            } else if (line.startsWith("m:")) {
                content.append(line.replaceFirst("m:", "")).append("\n");
                ignoreNextBlank = false;
            } else if (line.startsWith("END")) {
                messages.add(new Message(subject.toString(), content.toString()));
                subject.delete(0, subject.length());
                content.delete(0, content.length());
                ignoreNextBlank = true;
            } else if (ignoreNextBlank) {
            } else {
                content.append(line).append("\n");
            }
        }
        //Checks is any message has been loaded
        if (messages.size() == 0)
            throw new RuntimeException("No message detected");

        return messages;
    }

    /**
     * Get the config from the config file
     *
     * @throws IOException if an error occurred when file is open or read
     */
    private void getConfigFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(CFG_PATH,
                StandardCharsets.UTF_8));

        Properties prop = new Properties();
        prop.load(reader);
        serverAddress = prop.getProperty("serverAddress");
        serverPort = Integer.parseInt(prop.getProperty("serverPort"));
        numberOfGroups = Integer.parseInt(prop.getProperty("groups"));
    }

    /**
     * Gets the regex used to verify if an email address is valid according to RFC 5322
     *
     * @return The pattern used to match the mail conformity
     * @throws IOException If there is an error for handling the file containing the regex
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc5322"></a>
     */
    private static Pattern getRegexFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(REGEX_PATH,
                StandardCharsets.UTF_8));

        String line;
        StringBuilder regex = new StringBuilder();
        while ((line = reader.readLine()) != null)
            if (!line.startsWith("Source="))
                regex.append(line);

        return Pattern.compile(regex.toString());
    }

    /**
     * Ensures that the email address complies with RFC 5322
     * @param address
     * @return
     */
    private boolean isAddressValid(String address) {
        Matcher matcher = MAIL_PATTERN.matcher(address);
        return matcher.find();
    }

    /**
     * Get the server port
     *
     * @return the server port as int
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * Get the server address
     *
     * @return the server address as String
     */
    public String getServerAddress() {
        return serverAddress;
    }

    /**
     * Get the messages
     *
     * @return a list of Message
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Get the victimes
     *
     * @return a list of Victim
     */
    public List<Victim> getVictims() {
        return victims;
    }

    /**
     * Get the number of Group
     *
     * @return the number of groups
     */
    public int getNumberOfGroups() {
        return numberOfGroups;
    }
}
