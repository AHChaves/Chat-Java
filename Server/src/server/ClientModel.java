package server;

import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author hashi
 */
public class ClientModel {

    public PrintStream ps;
    public Socket client;

    public ClientModel(PrintStream ps, Socket client) {
        this.ps = ps;
        this.client = client;
    }
}
