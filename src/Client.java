import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    Socket s;
    DataInputStream in_s;
    DataOutputStream out_s;
    Scanner sc;

    public Client(Socket socket) {
        try {
            s = socket;
            in_s = new DataInputStream(s.getInputStream());
            out_s = new DataOutputStream(s.getOutputStream());
            sc = new Scanner(System.in);
        } catch (Exception e) {
            System.out.println("Error: Server not found");
        }
    }

    public void listenForMessages() {
        try {
            System.out.println("Listening for messages...");
            String incoming_msg = "";
            while (s.isConnected()) {
                incoming_msg = in_s.readUTF();
                System.out.println(incoming_msg);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sendMessages() {
        try {
            if (s.isConnected()) {
                System.out.println("Connected to Server.");
                while (s.isConnected()) {
                    out_s.writeUTF(sc.nextLine());
                    out_s.flush();
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws IOException {
        try {
            Client myC =  new Client(new Socket("localhost", 6969));
            Thread t1 = new Thread(myC);
            t1.start();
            myC.sendMessages();
        } catch (Exception e) {
            System.out.println("Error: Server not found");
        }
    }

    @Override
    public void run() {
        System.out.println("\n[i]  listening thread started\n\n");
        listenForMessages();
    }
}
