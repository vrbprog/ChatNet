import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{
    private Server server;
    private Socket socket;
    private InputStream is;
    private OutputStream os;
    private Scanner in;
    private PrintStream out;
    private final int number;

    public Client(Socket socket, Server server, int number){
        this.server = server;
        this.socket = socket;
        this.number = number;
        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();
            in = new Scanner(is);
            out = new PrintStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        String inputMessage;
        out.println("Вас приветствует чат-сервер!");
        while(!(inputMessage = in.nextLine()).equals("exit")){
            System.out.println(number + ": " + inputMessage);
            server.broadcastMessage(number + ": " + inputMessage, number);
        }
        server.deleteClient(this);
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        out.println(message);
    }

    public int getNumber() {
        return number;
    }
}
