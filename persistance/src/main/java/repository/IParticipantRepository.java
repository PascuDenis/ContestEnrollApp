package repository;

import model.participant.AuditionDTO;
import model.participant.ParticipantDTO;

import java.util.List;

public interface IParticipantRepository extends ICrudRepository<Integer, ParticipantDTO>{
    List<AuditionDTO> getAuditionsForOneParticipant(Integer id);
    Integer countNumberOfParticipantForOneAudition(AuditionDTO auditionId);
}
