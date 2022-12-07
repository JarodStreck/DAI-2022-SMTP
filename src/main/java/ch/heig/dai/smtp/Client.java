package ch.heig.dai.smtp;

import ch.heig.dai.mail.Mail;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

public class Client {
    private Socket socket;
    private BufferedReader is;
    private BufferedWriter os;

    private static final Logger LOG = Logger.getLogger(Client.class.getName());
    private static final String EOL = "\r\n";

    public boolean isConnected() {
        return !socket.isClosed();
    }

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
            write("MAIL FROM:" + mail.getSenderMail());
            for(String s : mail.getRecipientsMail())
                write("RCPT TO:" + s);

            write("DATA");
            //TODO on peut remplacer ça par un seul write() en mettant tout dedans
            os.write("Content-Type: text; charset=utf-8" + EOL);
            os.write("From: " + mail.getSenderMail() + EOL);
            os.write("To: " + mail.getRecipientsMail().get(0));
            for (int i = 1; i < mail.getRecipientsMail().size(); i++) {
                os.write(", " + mail.getRecipientsMail().get(i));
            }
            os.write(EOL);
            // Reference for base64 encoding
            // https://www.telemessage.com/developer/faq/how-do-i-encode-non-ascii-characters-in-an-email-subject-line/
            os.write("Subject:=?utf-8?B?" + Base64.getEncoder().encodeToString(mail.getSubject().getBytes())
                    + "?=" + EOL);
            os.write(EOL);
            os.flush();
            os.write(mail.getContent() + EOL);
            write(".");
            line = is.readLine();
            LOG.info(line);
            LOG.info("Mail delivered.\n");

        } catch (IOException ioe) {
            System.err.println("Error reading the input");
        } catch (RuntimeException re) {
            System.err.println(re.getMessage());
        }
    }

    public boolean connect(String host, int port) {
        try {
            this.socket = new Socket(host, port);
            os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),
                    StandardCharsets.UTF_8));
            is = new BufferedReader(new InputStreamReader(socket.getInputStream(),
                    StandardCharsets.UTF_8));
            return true;
        } catch (UnknownHostException uhe) {
            System.err.println("Cannot connect to the host : " + host);
            return false;
        } catch (IOException ioe) {
            System.err.println("An I/O error occurred when creating the socket");
            return false;
        } catch (IllegalArgumentException iae) {
            System.err.println("The given port (" + port + ") is not allowed");
            return false;
        } catch (SecurityException se) {
            System.err.println("Security exception occurred during connexion");
            return false;
        }
    }

    public void close(){
        try{
            write("QUIT");
            is.close();
            os.close();
            socket.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void write(String request) {
        try {
            os.write(request + EOL);
            os.flush();
            checkResponse();
        } catch (IOException ioe_os) {
            ioe_os.printStackTrace();
            throw new RuntimeException("An I/O error occurred writing the request");
        }
    }

    private void checkResponse() throws IOException {
        String line = is.readLine();
        if (!line.startsWith("250")) {
            throw new IOException("SMTP response error : " + line);
        }
        LOG.info(line);
    }


}
