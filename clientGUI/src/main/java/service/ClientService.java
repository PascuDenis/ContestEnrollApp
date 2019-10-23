package service;

import model.participant.AuditionDTO;
import model.participant.ParticipantDTO;
import repository.server.ParticipantAuditionServerRepository;
import repository.server.ParticipantServerRepository;

import java.util.List;

public class ClientService {
    ParticipantServerRepository participantServerRepository;
    ParticipantAuditionServerRepository participantAuditionServerRepository;

    public ClientService(ParticipantServerRepository participantServerRepository, ParticipantAuditionServerRepository participantAuditionServerRepository) {
        this.participantServerRepository = participantServerRepository;
        this.participantAuditionServerRepository = participantAuditionServerRepository;
    }

    public List<ParticipantDTO> findAllParticipants(){
        return participantServerRepository.findAll();
    }

    public List<AuditionDTO> findAuditionsForOneParticipant(ParticipantDTO participant){
        return  participantServerRepository.getAuditionsForOneParticipant(participant);
    }

    public int countParticipantsForOneAudition(AuditionDTO audition){
        return participantServerRepository.countNumberOfParticipantForOneAudition(audition);
    }

    public void save(ParticipantDTO participant){
        System.out.println("cs save");
        participantServerRepository.save(participant);
    }

    public void delete(Integer idParticipant){
        participantServerRepository.delete(idParticipant);
    }

    public void update(ParticipantDTO participant){
        participantServerRepository.update(participant);
    }
}
