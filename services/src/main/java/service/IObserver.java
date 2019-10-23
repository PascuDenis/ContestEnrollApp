package service;

import model.participant.ParticipantDTO;
import model.user.UserDTO;

import java.util.List;

public interface IObserver  {
    void receivedModification(List<ParticipantDTO> participantDTOList) throws SendException;
    void userLogedIn(UserDTO userDTO) throws SendException;
    void userLogedOut(UserDTO userDTO) throws SendException;
}
