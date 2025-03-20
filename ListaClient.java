import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ListaClient {
    // Lista che contiene i socket di tutti i client connessi al server
    private ArrayList<Socket> listaSockets;

    // Costruttore: inizializza la lista dei socket
    public ListaClient() {
        listaSockets = new ArrayList<Socket>();
    }

    // Metodo sincronizzato per aggiungere un nuovo client alla lista
    // Il "synchronized" serve a evitare conflitti se più thread accedono contemporaneamente
    public synchronized void addClient(Socket c) throws IOException {
        listaSockets.add(c);
    }

    // Metodo sincronizzato per rimuovere un client dalla lista (in base alla posizione)
    public synchronized void removeClient(int i) throws IOException {
        // Chiude il socket del client da rimuovere
        listaSockets.get(i).close();

        // Rimuove il client dalla lista
        listaSockets.remove(i);
    }

    // Metodo sincronizzato per inviare un messaggio a tutti i client connessi, tranne al mittente
    public synchronized void sendAll(String msg, Socket client) throws IOException {
        for (Socket socket : listaSockets) {
            // Il messaggio non viene inviato al client che lo ha originato
            if (socket != client) {
                // Crea lo stream di output verso quel client
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                // Invia il messaggio
                out.println(msg);
                out.flush(); // Forza l’invio del messaggio
            }
        }
    }
}
