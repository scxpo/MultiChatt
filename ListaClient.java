import java.io.IOException;
import java.io.PrintWriter;
import java.net. Socket;
import java.util.ArrayList;


public class ListaClient {
    private ArrayList<Socket> listaSockets;
    public ListaClient() {
        listaSockets = new ArrayList<Socket>();
    }
    public synchronized void addClient(Socket c) throws IOException {
        listaSockets.add(c);
    }

    public synchronized void removeClient(int i) throws IOException {
        listaSockets.get(i).close();
        listaSockets.remove(1);
    }

    public synchronized void sendAll(String msg, Socket client) throws IOException {
        for (Socket socket : listaSockets) {
            if (socket != client) {
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println(msg);
                out.flush();
            }
        }
    }

}