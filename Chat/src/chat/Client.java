package chat;

import static chat.chat_client.login;
import static chat.chat_client.msg_textt;
import static chat.chat_client.thread_client;
import java.io.PrintStream;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author hashi
 */
public class Client implements Runnable {

    private String host;
    private int port;
    public Socket socket;
    public String username;
        public PrintStream output;
    static ClientReceiver receiver;
    public boolean envia = false;
    public boolean close = false;


    public Client(String host, int port, String username) {
        this.host = host;
        this.port = port;
        this.username = username;
        try {
            this.socket = new Socket(this.host, this.port);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        String msg;
        
        if (socket == null) {
            JOptionPane.showMessageDialog(null,  "Unable to connect. Something is wrong!", "title", JOptionPane.WARNING_MESSAGE);
            close = true;
            chat_client.msg_textt.setEditable(false);
            chat_client.msg_send.setEnabled(false);
            
        } else {
            msg = "Client has been connected.";

            try {
                receiver = new ClientReceiver(socket.getInputStream());
                new Thread(receiver).start();

                receiver.msgServer(msg);

                output = new PrintStream(socket.getOutputStream());

                while (true) {
                    Thread.sleep(1);
                    if (envia == true) {
                        output.println("[" + username + "]: " + msg_textt.getText());
                        receiver.msgUser(msg_textt.getText());
                        envia = false;
                        msg_textt.setText("");
                    }

                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

}
