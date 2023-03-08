package letsgo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9090;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        Scanner sc = new Scanner(System.in);
        PrintWriter dout = new PrintWriter(socket.getOutputStream(), true);
        System.out.println(in.readLine());

        while (true) {
            System.out.print(" ~ ");

            String command = sc.nextLine();

            if (command.equals("exit")) {
                break;
            }

            dout.println(command);

            String serverResponse = in.readLine();
            System.out.println("Server says: " + serverResponse);

            if (serverResponse.contains("Error") || serverResponse.contains("Success")
                    || serverResponse.contains("Message")) {
                serverResponse = in.readLine();
                System.out.println("Server says: " + serverResponse);
            }

            dout.flush();
        }
        socket.close();
        System.exit(0);
    }
}
