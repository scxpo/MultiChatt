import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadRicevi implements Runnable {
    private Socket socket;              // Socket collegato al server
    BufferedReader in;                 // Stream di input per leggere i messaggi in arrivo

    // Costruttore: riceve il socket e prepara lo stream per leggere dal server
    public ThreadRicevi(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    // Metodo run che viene eseguito nel thread
    public void run() {
        String messaggio;
        try {
            // Legge il primo messaggio dal server
            messaggio = in.readLine();

            // Finché il server invia messaggi (cioè il messaggio ricevuto non è null)
            while (messaggio != null) {
                // Stampa il messaggio ricevuto dal server
                System.out.println(messaggio);

                // Legge il messaggio successivo
                messaggio = in.readLine();
            }

            // Se il server chiude la connessione, esce dal ciclo
            System.out.println("Server chiuso");
            socket.close(); // Chiude il socket localmente

        } catch (IOException e) {
            // In caso di errore nella connessione o nella lettura dei dati
            System.out.println("Errore di connessione");
        }
    }
}
