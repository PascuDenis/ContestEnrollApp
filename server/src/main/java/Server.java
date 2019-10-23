import com.google.gson.Gson;
import model.server.Request;
import model.server.RequestType;
import repository.utils.AbstractIObservable;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class Server extends AbstractIObservable {
    private ServerSocket serverSocket;
    private Gson gson;

    public Server(Properties properties){
        try{
            serverSocket = new ServerSocket(Integer.parseInt(properties.getProperty("port")));
            gson = new Gson();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer(){
        while (true){
            try{
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + " . . .");
                Socket server = serverSocket.accept();
                DataInputStream inputStream = new DataInputStream(server.getInputStream());
                String input = inputStream.readUTF();
                Request request = gson.fromJson(input, Request.class);
                RequestWorker worker = null;
                if (request.getRequestType() == RequestType.NormalRequest)
                    worker = new RequestWorker(server, Boolean.FALSE, IObserverList);
                else if (request.getRequestType() == RequestType.ObserverRequest) {
                    worker = new RequestWorker(server, Boolean.TRUE, null);
                    add(worker);
                }
                Thread tw = new Thread(worker);
                tw.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
