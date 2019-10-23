package service;

import model.participant.ParticipantDTO;
import model.user.UserDTO;

import java.util.List;

public interface IServer {
    void login(UserDTO userDTO, IObserver client) throws SendException;
    void sendData(List<ParticipantDTO> participantDTOList) throws SendException;
    void logout(UserDTO userDTO, IObserver client) throws  SendException;
    ParticipantDTO[] getLoggedUsers(UserDTO userDTO) throws SendException;
}
