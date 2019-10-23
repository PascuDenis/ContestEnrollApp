package utils;

import service.IServer;

import java.net.Socket;

public class ObjectConcurrentServer extends AbstractConcurentServer {
    private IServer server;
    public ObjectConcurrentServer(int port, IServer server) {
        super(port);
        this.server = server;
        System.out.println("ObjectConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
//        ClientObjectWorker worker = new ClientObjectWorker(server, client);
//        Thread thread = new Thread(worker);
        return null;
    }
}
