import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadInvio implements Runnable {
    private Scanner sc;          // Oggetto per leggere input da tastiera
    private PrintWriter out;     // Oggetto per inviare messaggi al server

    // Costruttore: prende il socket e inizializza gli stream
    public ThreadInvio(Socket socket) throws IOException {
        sc = new Scanner(System.in);                 // Scanner per leggere da tastiera
        out = new PrintWriter(socket.getOutputStream()); // Stream di output verso il server
    }

    // Metodo run che viene eseguito nel thread
    public void run() {
        String message;
        boolean primo = true; // Flag per il primo messaggio (che sarà il nome utente)

        // Ciclo infinito finché il thread non viene interrotto
        while (!Thread.interrupted()) {
            if (primo) {
                // Messaggio per l'utente: inserire il proprio nome
                System.out.println("Inserire nome utente");
            }

            // Legge una riga da tastiera
            message = sc.nextLine();

            // Invia il messaggio al server
            out.println(message);
            out.flush(); // Invio immediato del messaggio

            if (primo) {
                // Dopo il primo messaggio (nome utente), stampa messaggio guida
                System.out.println("Inserisci messaggio");
                primo = false; // Ora inizia la fase normale di chat
            }
        }
    }
}
