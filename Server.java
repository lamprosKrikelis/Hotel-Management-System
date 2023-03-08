package letsgo;

import java.io.IOException;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.util.ArrayList;
        import java.util.concurrent.ExecutorService;
        import java.util.concurrent.Executors;

public class Server {

    private  static final int PORT = 9090 ;
    private static ExecutorService pool = Executors.newFixedThreadPool(5);
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    public static void main(String[] args) throws IOException {

        ServerSocket listener = new ServerSocket(PORT);

        while (true) {

            System.out.print("Please wait for connection :) ");
            Socket client = listener.accept();
            System.out.println("We are connected  :) ");
            ClientHandler clientThread = new ClientHandler(client);
            clients.add(clientThread);
            pool.execute(clientThread);
        }
    }
}