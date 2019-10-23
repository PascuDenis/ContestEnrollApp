package repository.utils;

import model.participant.AuditionDTO;
import model.participant.ParticipantDTO;
import model.user.UserDTO;

import java.util.List;

public interface IServerService {
    boolean login(UserDTO user, IObserver observer);
    void save(ParticipantDTO participant);
    void delete(Integer participantId);
    void update(ParticipantDTO participant);
    List<ParticipantDTO> getAllParticipants();
    List<AuditionDTO> getAuditionListForOneParticipant(ParticipantDTO participant);
    int countNumberOfParticipantForOneAudition(AuditionDTO audition);
}
