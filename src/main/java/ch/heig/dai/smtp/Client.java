package ch.heig.dai.smtp;

import ch.heig.dai.mail.Mail;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Logger;

/**
 * Handles the connection to the SMTP server and the email sending process
 *
 * @author Jarod Streckeisen, Timothee Van Hove
 */
public class Client {
    private Socket socket;
    private BufferedReader is;
    private BufferedWriter os;
    private static final Logger LOG = Logger.getLogger(Client.class.getName());
    private static final String EOL = "\r\n";

    /**
     * Send an email to the SMTP server from a Mail object
     *
     * @param mail mail that we want to send
     * @see <a href="https://www.telemessage.com/developer/faq/how-do-i-encode-non-ascii-characters-in-an-email-subject-line/">base64 encoding</a>
     */
    public void send(Mail mail) {
        try {
            LOG.info("Send mail with SMTP protocol.");
            String line = is.readLine();
            LOG.info(line);
            write("EHLO localhost");
            while (line.startsWith("250-")) {
                line = is.readLine();
                LOG.info(line);
            }
            //Sender
            write("MAIL FROM:" + mail.getSenderMail());
            for (String s : mail.getRecipientsMail())
                write("RCPT TO:" + s);

            //Data of the mail
            write("DATA");
            StringBuilder request = new StringBuilder();
            request.append(mail.getContentType()).append(" charset=\"utf-8\"" + EOL);
            request.append("From: ").append(mail.getSenderMail()).append(EOL);
            request.append("To: ").append(mail.getRecipientsMail().get(0));
            for (int i = 1; i < mail.getRecipientsMail().size(); i++)
                request.append(", ").append(mail.getRecipientsMail().get(i));

            request.append(EOL);
            request.append("Subject:=?utf-8?B?").append(
                    Base64.getEncoder().encodeToString(mail.getSubject().getBytes())).append(
                    "?=" + EOL);
            request.append(EOL);
            request.append(mail.getContent());

            //End sequence
            request.append(EOL).append(".");
            write(request.toString());
            line = is.readLine();
            LOG.info(line);
            LOG.info("Mail delivered.\n");

        } catch (IOException ioe) {
            System.err.println("Error reading the input");
        } catch (RuntimeException re) {
            System.err.println(re.getMessage());
        }
    }

    /**
     * Connect to the SMTP server
     *
     * @param host address of the server
     * @param port port of the server
     */
    public void connect(String host, int port) {
        try {
            this.socket = new Socket(host, port);
            os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),
                    StandardCharsets.UTF_8));
            is = new BufferedReader(new InputStreamReader(socket.getInputStream(),
                    StandardCharsets.UTF_8));
        } catch (UnknownHostException uhe) {
            System.err.println("Cannot connect to the host : " + host);
        } catch (IOException ioe) {
            System.err.println("An I/O error occurred when creating the socket");
        } catch (IllegalArgumentException iae) {
            System.err.println("The given port (" + port + ") is not allowed");
        } catch (SecurityException se) {
            System.err.println("Security exception occurred during connexion");
        }
    }

    /**
     * Close all ressources used by the SMTP client
     */
    public void close() {
        try {
            write("QUIT");
            is.close();
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send a message to the SMTP server
     *
     * @param request string containing the message to send
     */
    private void write(String request) {
        try {
            os.write(request + EOL);
            os.flush();
            checkResponse();
        } catch (IOException ioe_os) {
            ioe_os.printStackTrace();
            throw new RuntimeException("An I/O error occurred writing the request");
        }
    }

    /**
     * Get and check the response of the server
     *
     * @throws IOException if the server send a bad response
     */
    private void checkResponse() throws IOException {
        String line = is.readLine();
        if (!line.startsWith("250")) {
            throw new IOException("SMTP response error : " + line);
        }
        LOG.info(line);
    }
}
