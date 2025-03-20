import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainServer {
    public static void main(String[] args){
        final int PORT = 5500; // Porta su cui il server resta in ascolto

        try {
            // Creazione del ServerSocket che accetta le connessioni in ingresso sulla porta indicata
            ServerSocket serverSocket = new ServerSocket(PORT);

            // Lista che conterrà tutti i thread che gestiscono le connessioni con i client
            ArrayList<Thread> listaThreadConnessioni = new ArrayList<>();

            // Lista che conterrà tutti i socket dei client connessi (presumibilmente gestita da una classe ListaClient)
            ListaClient listaClient = new ListaClient();

            // Messaggi di stato per l'avvio del server
            System.out.println("Server Aperto");
            System.out.println("In attesa di connessioni...");

            // Ciclo infinito per accettare continuamente nuove connessioni
            while(true){
                // Accetta una nuova connessione da un client
                Socket nuovoClient = serverSocket.accept();

                // Aggiunge il nuovo client alla lista dei client attivi
                listaClient.addClient(nuovoClient);

                // Crea un nuovo thread per gestire la connessione con questo specifico client
                Thread connessioneThread = new Thread(new ThreadConnessione(nuovoClient, listaClient));

                // Aggiunge il thread alla lista dei thread attivi
                listaThreadConnessioni.add(connessioneThread);

                // Avvia il thread (gestione della comunicazione con il client)
                listaThreadConnessioni.getLast().start();
            }

        } catch (IOException e) {
            // Messaggio di errore se qualcosa va storto nella creazione del server o durante l'accettazione
            System.out.println("Errore di connessione");
        }
    }
}
