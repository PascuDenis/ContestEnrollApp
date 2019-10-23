package repository;

import model.participant.AuditionDTO;

public interface IParticipantAuditionRepository extends ICrudRepository<Integer, AuditionDTO> {
    @Override
    void save(AuditionDTO entity);
    void save(Integer idParticipant, Integer idAudition);

}
