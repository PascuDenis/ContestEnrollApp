package objectprotocol;

import model.participant.ParticipantDTO;
import model.user.UserDTO;
import service.IObserver;
import service.IServer;
import service.SendException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientObjectWorker implements Runnable, IObserver {
    private IServer server;
    private Socket connection;

    private ObjectInputStream inputStream;
    protected ObjectOutputStream outputStream;
    private volatile boolean connected;

    ClientObjectWorker(IServer server, Socket connection){
        this.server = server;
        this.connection = connection;
        try{
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }

    @Override
    public void receivedModification(List<ParticipantDTO> participantDTOList) throws SendException {

    }

    @Override
    public void userLogedIn(UserDTO userDTO) throws SendException {

    }

    @Override
    public void userLogedOut(UserDTO userDTO) throws SendException {

    }
}
