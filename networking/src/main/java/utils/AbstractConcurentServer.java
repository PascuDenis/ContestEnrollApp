package utils;

import java.net.Socket;

public abstract class AbstractConcurentServer extends AbstractServer {

    public AbstractConcurentServer(int port){
        super(port);
        System.out.println("Concurrent AbstractServer");
    }
    @Override
    protected void processRequest(Socket client) {
        Thread thread = createWorker(client);
    }

    protected abstract Thread createWorker(Socket client);
}
