import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    String name;
    Socket socket;
    DataInputStream in_s;
    DataOutputStream out_s;
    static ArrayList<ClientHandler> activeClients = new ArrayList<>();

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            in_s = new DataInputStream(socket.getInputStream());
            out_s = new DataOutputStream(socket.getOutputStream());
            activeClients.add(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void handleChatBetweenClients() {
        while (socket.isConnected() && this.name != null) {
            try {
                String msg = in_s.readUTF();
                broadcastToOtherClients(this.name + ": " +msg);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private void broadcastToOtherClients(String msg) {
        for (ClientHandler u : activeClients) {
            if (u != this)
                u.receiveBroadcast(msg);
        }
    }

    public void receiveBroadcast(String msg) {
        try {
            out_s.writeUTF(msg);
            out_s.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        try {
            out_s.writeUTF("Enter your name for the group chat: ");
            out_s.flush();
            name = in_s.readUTF();
            handleChatBetweenClients();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
