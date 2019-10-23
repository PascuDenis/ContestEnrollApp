package service;

import model.participant.AuditionDTO;
import model.participant.ParticipantDTO;
import model.user.UserDTO;
import repository.ILoginRepository;
import repository.RepositoryException;
import repository.databaseRepository.ParticipantAuditionDBRepository;
import repository.databaseRepository.ParticipantDBRepository;
import repository.utils.AbstractIObservable;
import repository.utils.IObserver;
import repository.utils.IServerService;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

public class ServerService extends AbstractIObservable implements Serializable, IServerService {
    private ParticipantDBRepository participantDBRepository;
    private ParticipantAuditionDBRepository participantAuditionDBRepository;
    private ILoginRepository databaseLoginRepository;

    public ServerService(ParticipantDBRepository participantDBRepository, ParticipantAuditionDBRepository participantAuditionDBRepository, ILoginRepository databaseLoginRepository) {
        this.participantDBRepository = participantDBRepository;
        this.participantAuditionDBRepository = participantAuditionDBRepository;
        this.databaseLoginRepository = databaseLoginRepository;
    }

    @Override
    public boolean login(UserDTO userDTO, IObserver observer) {
        System.out.println("sserver searching for user");
        boolean response =  databaseLoginRepository.searchUser(userDTO);
        if (response){
            add(observer);
        }
        return response;
    }

    @Override
    public void save(ParticipantDTO participant) {
        System.out.println("sserver saving participant");
        int index = participantDBRepository.getIndexOfLastParticipant();
        participantDBRepository.save(participant);
        for (int i = 0; i < participant.getAuditionDTOList().size(); i++) {
            participantAuditionDBRepository.save(index + 1, participant.getAuditionDTOList().get(i).getId());
        }
        try{
            notifyObservers();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer participantId){
        System.out.println("sserver removing participant");
        try {
            participantAuditionDBRepository.delete(participantId);
        }
        catch (RepositoryException e) {
            System.out.println(e);
        }
        finally {
            participantDBRepository.delete(participantId);
        }
        try{
            notifyObservers();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ParticipantDTO participant){
        System.out.println("sserver updating participant");
        try {
            participantAuditionDBRepository.delete(participant.getId());
        } catch (RepositoryException e){
            System.out.println(e);
        }
        finally {
            participantDBRepository.update(participant);
            for (int i =0; i< participant.getAuditionDTOList().size(); i++){
                participantAuditionDBRepository.save(participant.getId(), participant.getAuditionDTOList().get(i).getId());
            }
        }
        try{
            notifyObservers();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ParticipantDTO> getAllParticipants(){
        System.out.println("sserver searching for participants");
        List<ParticipantDTO> participantDTOList;

        participantDTOList = participantDBRepository.findAll();
        int number = participantDTOList.size();
        for (int i = 1; i< number; i++){
            if (participantAuditionDBRepository.findAll(i) != null){
                participantDTOList.get(i).setAuditionDTOList(participantAuditionDBRepository.findAll(participantDTOList.get(i).getId()));
                System.out.println(participantDTOList.get(i));

            }
        }
        return participantDTOList;
    }

    @Override
    public List<AuditionDTO> getAuditionListForOneParticipant(ParticipantDTO participant){
        System.out.println("sserver getAuditionListForOneParticipant");
        return participantDBRepository.getAuditionsForOneParticipant(participant.getId());
    }

    public int countNumberOfParticipantForOneAudition(AuditionDTO audition){
        System.out.println("sserver countNumberOfParticipantForOneAudition");
        return participantDBRepository.countNumberOfParticipantForOneAudition(audition);
    }
}
