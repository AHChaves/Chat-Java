
package server;

import static server.chat_server.msg_area;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hashi
 */
public class Servidor implements Runnable {

    private int port;
    public static List<ClientModel> ConnectedClients;
    

    public Servidor(int door) {
        this.port = door;
        this.ConnectedClients = new ArrayList<ClientModel>();
    }

    public void removeClient(Socket client){
        ConnectedClients.removeIf(c -> client.getInetAddress().getHostAddress().compareTo(c.client.getInetAddress().getHostAddress()) != 0);
        msg_area.setText(msg_area.getText() + "<Server>: Client ["+client.getInetAddress().getHostAddress() + "] Disconect from the chat");
        try {
            client.close();
        } catch (IOException e) {
            
        }
    }


    @Override
    public void run() {

        try {
            ServerSocket server = new ServerSocket(this.port);
            msg_area.setText("Server started in port: " + port + "\n");

            while (true) {
                Socket client = server.accept();
                msg_area.setText(msg_area.getText() + "\nConnected with a new client: "
                        + client.getInetAddress().getHostAddress());

                PrintStream ps = new PrintStream(client.getOutputStream());
                
                this.ConnectedClients.add(new ClientModel(ps, client));

                ClientThread threadClient = new ClientThread(client.getInputStream(), this, client);
                new Thread(threadClient).start();
  
                if (this.ConnectedClients.size() < 0) {
                    break;
                }
            }
            server.close();
        } catch (IOException e) {
        }
    }

    public void ShareMessage(String message, Socket author) {
        msg_area.setText(msg_area.getText() + "\n" + message);

        for (ClientModel actualClient : ConnectedClients) {
            //if(actualClient.client.getInetAddress().getHostAddress().compareTo(author.getInetAddress().getHostAddress()) != 0 ){
                actualClient.ps.println(message);
            //}
        }

    }
}
