import java.net.*;
import java.io.*;

public class Client {
    private Socket socket = null;
    private BufferedReader input = null;
    private BufferedWriter output = null;

    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            input = new BufferedReader(new InputStreamReader(System.in));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while (true) {
                String message = input.readLine();
                if (message.equals("exit")) break;
                output.write(message);
                output.newLine();
                output.flush();
            }

            socket.close();
            input.close();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 8080);
    }
}