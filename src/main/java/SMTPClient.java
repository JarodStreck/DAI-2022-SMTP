import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class SMTPClient {
    private Socket socket;
    private BufferedReader is;
    private BufferedWriter os;

    public boolean isConnected() {
        return !socket.isClosed();
    }

    public void run(){
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String response;
        try {
            if (this.connect("localhost", 25)) {
                System.out.println(this.read());
                while (this.isConnected()) {
                    //Write request
                    this.write(stdin.readLine());

                    //Read response
                    response = this.read();
                    System.out.println(response);
                    processMsg(response);
                }
            }
        } catch (IOException ioe) {
            System.err.println("Error reading the input");
        } catch (RuntimeException re) {
            System.err.println(re.getMessage());
        }
    }
    boolean connect(String host, int port) {
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

    public void write(String request) {
        try {
            //Don't forget the endline, otherwise the server won't read the request
            os.write(request.concat("\n"));
            os.flush();
        } catch (IOException ioe_os) {
            throw new RuntimeException("An I/O error occurred writing the request");
        }
    }

    public String read() throws IOException {
        try {
            StringBuilder response = new StringBuilder();
            while(is.ready() || response.length() == 0)
                response.append(is.readLine().concat("\n"));
            return response.toString();
        } catch (IOException ioe_is) {
            throw new IOException("An I/O error occurred reading the response");
        }
    }
    public String processMsg(String msg){

        return msg;
    }

}
