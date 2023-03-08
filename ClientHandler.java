package letsgo;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class ClientHandler implements Runnable {

    public String request;
    public Socket client;
    public BufferedReader in;
    public PrintWriter dout;
    String[] search = new String[2];

    public ClientHandler(Socket clientSocket) throws IOException {
        this.client = clientSocket;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        dout = new PrintWriter(client.getOutputStream(), true);
        dout.println(Arrays.deepToString(Search(search)));
    }

    public String[] Search(String[] search) {

        this.search[0] = "Search for hotels buy typing `city` ";
        this.search[1] = " Search by  avalable dates ";
        return this.search;
    }

    @Override
    public void run() {
        try {

            Display display = new Display();
            while (true) {
                request = in.readLine();
                System.out.println("Request: " + request);
                if (request.contains("city")) {
                    ArrayList<String> file = display.getFile(dout, in);
                    dout.println(file.toString());
                    if (file.size() > 1) {
                        String f = in.readLine();
                        display.Reservetion(dout, in);
                        dout.println(Arrays.deepToString(Search(search)));
                    }
                } else {
                    dout.println(" Pls type city  ");
                }
            }
        } catch (IOException e) {
            System.err.println("IO Exception ");
            System.err.println(Arrays.toString(e.getStackTrace()));
        } finally {
            dout.close();
            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
