/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chat;

import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author hashi
 */
public class ClientThread implements Runnable {

    private InputStream client;
    private Servidor server;

    public Servidor getServer() {
        return server;
    }

    private Socket clientIp;

    public ClientThread(InputStream client, Servidor server, Socket clientIp) {
        this.client = client;
        this.server = server;
        this.clientIp = clientIp;
    }

    @Override
    public void run() {
        // quando chegar uma msg, distribui pra todos
        Scanner s = new Scanner(this.client);
        while (s.hasNextLine()) {
            String msg = s.nextLine();
            if(msg.contains("<CLOSE>")){
                server.removeClient(clientIp);
                break;
            }
            else{
                server.ShareMessage(msg, clientIp);
            }
        }
        s.close();
    }
}
