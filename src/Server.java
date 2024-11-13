import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Server {
    ServerSocket ss;
    int activeUsers = 0;
//    ArrayList<ClientHandler> users = new ArrayList<>();

    public Server(ServerSocket serverSocket) {
        ss = serverSocket;
        System.out.println("Server started\nWaiting for a clients");
    }

    private void handleConnections() throws IOException {
        while (!ss.isClosed()) {
            Socket s = ss.accept(); // blocking call
            if (s.isConnected()) {
                activeUsers++;
                System.out.println("Active users: " + activeUsers);
                ClientHandler user = new ClientHandler(s);
                Thread t = new Thread(user);
                t.start();
            }
        }
    }

    public void closeServer() {
        try {
            System.out.println("Client disconnected\nClosing server...");
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void  broadcastMessage(String msg) {
        for (ClientHandler u : ClientHandler.activeClients) {
            u.receiveBroadcast(msg);
        }
    }

    public static void main(String[] args) throws IOException {
        Server s = new Server(new ServerSocket(6969));
        s.handleConnections();
    }
}
