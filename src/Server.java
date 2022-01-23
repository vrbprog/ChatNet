import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable{
    private ServerSocket server;
    private List<Client> clients = new ArrayList<>();
    private Client client;
    private int clientNumber = 1;

    public Server(int port) {
        try {
            server = new ServerSocket(port);
            new Thread(this).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Сервер запущен ...");
        try {
            while (true) {
                Socket socket = server.accept();
                System.out.println("Подключился новый клиент: " + clientNumber);
                client = new Client(socket, this, clientNumber++);
                clients.add(client);
                new Thread(client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMessage(String message, int number){
        System.out.println(message);
        for (Client client: clients) {
            if(client.getNumber() != number)
                client.sendMessage(message);
        }
    }

    public void deleteClient(Client client){
        clients.remove(client);
        System.out.println("Клиент " + client.getNumber() + " покинул чат");
    }
}
