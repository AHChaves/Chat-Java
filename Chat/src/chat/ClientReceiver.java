package chat;

import static chat.chat_client.Pane;
import static chat.chat_client.jScrollPane2;
import java.io.InputStream;
import java.util.Scanner;

/**
 *
 * @author hashi
 */
public class ClientReceiver implements Runnable {

    private InputStream server;

    public ClientReceiver(InputStream server) {
        this.server = server;
    }

   

    public void msgUser(String msg) {
        Message_right right = new Message_right(msg);
        Pane.add(right, "wrap, w 80%, al right");
        Pane.repaint();
        Pane.revalidate();
        jScrollPane2.getVerticalScrollBar().setValue(jScrollPane2.getVerticalScrollBar().getMaximum());
    } 
    
    public void msgServer(String msg) {
        Message_server status = new Message_server(msg);
        Pane.add(status, "wrap, w 80%");
        Pane.repaint();
        Pane.revalidate();
    }

    @Override
    public void run() {
        Scanner receiver = new Scanner(this.server);
        while (receiver.hasNextLine()) {
            Message_left text = new Message_left(receiver.nextLine());
            Pane.add(text, "wrap, w 80%");
            Pane.repaint();
            Pane.revalidate();
            jScrollPane2.getVerticalScrollBar().setValue(jScrollPane2.getVerticalScrollBar().getMaximum());

        }
        receiver.close();
    }
}
