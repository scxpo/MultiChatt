import java.io.IOException;
import java.net.Socket;

public class ClientMain {
    public static void main(String[] args) {
        Socket clientSocket;

        try {
            // Crea un socket e si connette al server in locale (127.0.0.1) sulla porta 5500
            clientSocket = new Socket("127.0.0.1", 5500);

            // Crea un thread per l'invio dei messaggi al server
            Thread invioThread = new Thread(new ThreadInvio(clientSocket));

            // Crea un thread per la ricezione dei messaggi dal server
            Thread riceviThread = new Thread(new ThreadRicevi(clientSocket));

            // Avvia il thread per inviare messaggi
            invioThread.start();

            // Avvia il thread per ricevere messaggi
            riceviThread.start();

        } catch (IOException e) {
            // In caso di errore nella connessione con il server, stampa messaggio di errore
            System.out.println("Impossibile connettersi con il server");
        }
    }
}
