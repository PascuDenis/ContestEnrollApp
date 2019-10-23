//package service;
//
//import model.participant.AuditionDTO;
//import model.participant.ParticipantDTO;
//import model.user.UserDTO;
//import repository.RepositoryException;
//import repository.loginRepository.DatabaseLoginRepository;
//
//import javax.xml.bind.ValidationException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Service {
//    private DatabaseRepository databaseRepostiory;
//    private DatabaseLoginRepository databaseLoginRepository;
//    private Validator validator;
//
//    public Service(DatabaseRepository databaseRepository, DatabaseLoginRepository databaseLoginRepository, Validator validator) {
//        this.databaseRepostiory = databaseRepository;
//        this.databaseLoginRepository = databaseLoginRepository;
//        this.validator = validator;
//    }
//
//    public List<ParticipantDTO> getAllParticipants() {
////        return databaseRepostiory.getParticipants();
//        return (List<ParticipantDTO>) databaseRepostiory.findAll();
//    }
//
//
//    public List<ParticipantDTO> getAllPartisipantForOneAudition(AuditionDTO auditionDTO) {
//        List<ParticipantDTO> participantDTOList = new ArrayList<>();
//            for (ParticipantDTO entity : databaseRepostiory.findAll()) {
//                System.out.println("A");
//                if (entity.getAuditionDTOList().contains(auditionDTO)) {
//                    System.out.println("B");
//                    participantDTOList.add(entity);
//                }
//            }
//        return participantDTOList;
//    }
//
//    public int countParticipantForOneAudition(AuditionDTO auditionDTO) {
//        int count = 0;
//        for (ParticipantDTO entity : databaseRepostiory.findAll())
//            if (entity.getAuditionDTOList().contains(auditionDTO))
//                count++;
//        return count;
//    }
//
//    public void save(ParticipantDTO entity) throws ValidationException {
//        try {
//            validator.validate(entity);
//        } catch (ValidationException e) {
//            System.err.println("The entity " + entity + " is not valid");
//            throw e;
//        }
//        int id = entity.getId();
//        for (ParticipantDTO object : databaseRepostiory.findAll()) {
//            if (object.getId() == entity.getId())
//                throw new RepositoryException("The ID already exists: " + id);
//        }
//        databaseRepostiory.save(entity);
//    }
//
//    public void delete(int id) {
//        ParticipantDTO object = databaseRepostiory.findOne(id);
//        if (object == null)
//            throw new RepositoryException("The entity with the id: " + id + " doesn't exists!");
//        databaseRepostiory.delete(id);
//        System.out.println("The entity was deleted sucessfully");
//    }
//
//    public void update(ParticipantDTO entity) throws ValidationException {
//        boolean exists = false;
//        try {
//            validator.validate(entity);
//        } catch (ValidationException e) {
//            e.printStackTrace();
//            System.err.println("The entity " + entity + "is not valid");
//            throw e;
//        }
//        if (databaseRepostiory.findAll() != null) {
//            for (ParticipantDTO object : databaseRepostiory.findAll()) {
//                if (object.getId() == entity.getId()) {
//                    exists = true;
//                    databaseRepostiory.update(entity);
//                }
//            }
//            if (!exists)
//                throw new RepositoryException("There is no entity to be updated!");
//        }
//    }
//
//    public boolean existUser(String username, String password){
//        UserDTO entity = databaseLoginRepository.findOne(username, password);
//        return entity != null;
//    }
//}
