import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainServer {
    public static void main(String[] args){
        final int PORT = 5500;
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            ArrayList<Thread> listaThreadConnessioni = new ArrayList<>();
            ListaClient listaClient = new ListaClient();
            System.out.println("Server Aperto");
            System.out.println("In attesa di connessioni...");
            while(true){
                Socket nuovoClient = serverSocket.accept();
                listaClient.addClient(nuovoClient);
                Thread connessioneThread = new Thread( new ThreadConnessione(nuovoClient, listaClient));
                listaThreadConnessioni. add(connessioneThread);
                listaThreadConnessioni.getLast(). start();
            }
        } catch (IOException e) {System.out.println("Errore di connessione");}
    }
}
