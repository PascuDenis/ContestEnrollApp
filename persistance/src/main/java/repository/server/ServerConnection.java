package repository.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

public class ServerConnection {
    public static Socket getSocketConnection(Properties props) {
        try {
            return new Socket(props.getProperty("host"), Integer.parseInt(props.getProperty("port")));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

