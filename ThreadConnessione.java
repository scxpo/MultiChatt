import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadConnessione implements Runnable {
    private Socket client;                     // Socket del client connesso
    private BufferedReader in;                 // Per leggere i messaggi inviati dal client
    private ListaClient listaClient;           // Riferimento alla lista di tutti i client connessi
    private String nomeClient;                 // Nome del client (inviato al primo messaggio)

    // Costruttore: inizializza la connessione con il client
    public ThreadConnessione(Socket client, ListaClient listaClient) throws IOException {
        this.client = client;
        this.listaClient = listaClient;

        // Si crea lo stream di input per leggere i messaggi inviati dal client
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        // Inizializza il nome del client con un valore provvisorio
        nomeClient = "errore";
    }

    // Metodo run che verrà eseguito nel thread
    public void run() {
        String messaggio;
        boolean primo = true; // Flag per identificare se è il primo messaggio del client (che sarà il nome)

        try {
            // Ciclo infinito finché il thread non viene interrotto
            while (!Thread.interrupted()) {
                // Legge il messaggio inviato dal client
                messaggio = in.readLine();

                // Se è il primo messaggio, lo considera come il nome del client
                if (primo) {
                    nomeClient = messaggio;
                    System.out.println(nomeClient + " Connesso"); // Messaggio nel server
                    primo = false;
                } else {
                    // Invia il messaggio a tutti gli altri client (broadcast)
                    listaClient.sendAll(nomeClient + ": " + messaggio, client);
                }
            }
        } catch (IOException e) {
            // Se la connessione si interrompe (es. il client chiude), viene mostrato un messaggio
            System.out.println("Connessione interrotta con " + nomeClient);
        }
    }
}
