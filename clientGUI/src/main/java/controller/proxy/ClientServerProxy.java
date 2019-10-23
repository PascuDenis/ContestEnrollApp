package controller.proxy;

import controller.controllerFX.Controller;
import model.participant.AuditionDTO;
import model.participant.ParticipantDTO;
import model.user.UserDTO;
import repository.utils.CommonUtils;
import repository.utils.IObserver;
import repository.utils.IServerService;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ClientServerProxy extends UnicastRemoteObject implements IObserver, Serializable {
    private IServerService server;
    private Controller client;

    public ClientServerProxy() throws RemoteException {
    }

    public void setClient(Controller client){
        this.client = client;
    }

    public boolean login(String username, String password){
        server = (IServerService) CommonUtils.getFactory().getBean("serverService");
        return server.login(new UserDTO(username, password), this);
    }

    public void save(ParticipantDTO participant){
        server.save(participant);
    }

    public void delete(Integer participantId){
        server.delete(participantId);
    }

    public void update(ParticipantDTO participant){
        server.update(participant);
    }

    public List<ParticipantDTO> getAllParticipants(){
        return server.getAllParticipants();
    }

    public List<AuditionDTO> getAuditionListForOneParticipant(ParticipantDTO participant){
        return server.getAuditionListForOneParticipant(participant);
    }

    public int countNumberOfParticipantForOneAudition(AuditionDTO audition){
        return server.countNumberOfParticipantForOneAudition(audition);
    }

    @Override
    public void update() throws RemoteException {
        client.update();
    }
}
