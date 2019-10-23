//package repository.databaseRepository;
//
//import model.participant.AuditionDTO;
//import model.participant.ParticipantDTO;
//import model.participant.Types;
//import model.participant.Distance;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import repository.ICrudRepository;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//
//public class DatabaseRepository{
//    private DatabaseConnection databaseConnection;
//    private List<ParticipantDTO> participantDTOList;
//    private final static String connectionSQLiteURL = "jdbc:sqlite:E:\\CS UBB\\Semestrul 4\\Medii de proiectare si programare\\DB\\lab2DB_TEST.db";
////        private final static String connectionSQLiteURL = "jdbc:sqlite:E:\\CS UBB\\Semestrul 4\\Medii de proiectare si programare\\DB\\lab2DB.db";
//    private static final Logger logger = LogManager.getLogger();
//
//
//    public DatabaseRepository(List<ParticipantDTO> participantDTOList, Properties props) {
//        logger.info("Initializing Repository.DatabaseRepository with propreties: {} ", props);
//        databaseConnection = new DatabaseConnection(props);
//        this.participantDTOList = participantDTOList;
//        searchParticipants(this.participantDTOList);
//    }
//
//
//    public int size() {
//        return 0;
//    }
//
//    public void save(ParticipantDTO entity) {
//        String querryParticipant = "INSERT INTO Participant VALUES (?,?,?)";
//        String querryParticipantAudition = "INSERT INTO ParticipantAudition VALUES (?,?)";
//        logger.traceEntry("saving partipant {} ", entity);
//
//        try (Connection connection = databaseConnection.getConnection();
//             PreparedStatement pstmt = connection.prepareStatement(querryParticipant)) {
//            pstmt.setInt(1, entity.getId());
//            pstmt.setString(2, entity.getName());
//            pstmt.setInt(3, entity.getAge());
//            pstmt.execute();
//        } catch (SQLException e) {
//            logger.error(e);
//            System.out.println("Error DB " + e);
//        }
//        int auditionNumber = entity.getAuditionDTOList().size();
//        for (int i = 0; i < auditionNumber; i++) {
//            try (Connection connection = databaseConnection.getConnection();
//                 PreparedStatement pstmt = connection.prepareStatement(querryParticipantAudition)) {
//                pstmt.setInt(1, entity.getId());
//                pstmt.setInt(2, entity.getAuditionDTOList().get(i).getId());
//                pstmt.execute();
//            } catch (SQLException e) {
////                logger.error(e);
//                System.out.println("Error DB " + e);
//            }
//        }
//        logger.traceExit();
////        Repository.DatabaseConnection.deconect();
//    }
//
//    @Override
//    public void delete(int id) {
//        String querryParticipant = "DELETE FROM Participant WHERE ID = ?";
//        String querryParticipantAudition = "DELETE FROM ParticipantAudition WHERE ParticipantID = ?";
//        logger.traceEntry("deleting participant with {}", id);
//
//        try (Connection connection = databaseConnection.getConnection();
//             PreparedStatement pstmt1 = connection.prepareStatement(querryParticipant);
//             PreparedStatement pstmt2 = connection.prepareStatement(querryParticipantAudition)) {
//            pstmt1.setInt(1, id);
//            pstmt2.setInt(1, id);
//            pstmt1.execute();
//            pstmt2.execute();
//        } catch (SQLException ex) {
//            logger.error(ex);
//            System.out.println("Error DB " + ex);
//        }
//        DatabaseConnection.deconect();
//    }
//
//    @Override
//    public void update(ParticipantDTO entity) {
//        String querryParticipant = "UPDATE Participant SET Name= ?, " +
//                "Age= ? " +
//                "WHERE ID= ?;";
//        String querryParticipantAudition = "INSERT INTO ParticipantAudition VALUES (?,?)";
//        logger.traceEntry("updatind participant{} ", entity);
//
//        try (Connection connection = databaseConnection.getConnection();
//             PreparedStatement pstmt1 = connection.prepareStatement(querryParticipant);
//             PreparedStatement pstmt2 = connection.prepareStatement(querryParticipantAudition);
//             PreparedStatement pstmt3 = connection.prepareStatement("DELETE FROM ParticipantAudition WHERE ParticipantID = ?")) {
//            pstmt1.setInt(3, entity.getId());
//            pstmt1.setString(1, entity.getName());
//            pstmt1.setInt(2, entity.getAge());
//            pstmt1.execute();
//            pstmt3.setInt(1, entity.getId());
//            pstmt3.execute();
//
//            int auditionNumber = entity.getAuditionDTOList().size();
//            for (int i = 0; i < auditionNumber; i++) {
//                pstmt2.setInt(1, entity.getId());
//                pstmt2.setInt(2, entity.getAuditionDTOList().get(i).getId());
//                pstmt2.execute();
//            }
//        } catch (SQLException ex) {
//            logger.error(ex);
//            System.out.println("Error DB "+ex);
//        }
//        DatabaseConnection.deconect();
//    }
//
//    public List<AuditionDTO> findAuditionForOneParticipant(int id) {
//        String querry = "SELECT *\n" +
//                "FROM Participant P\n" +
//                "  INNER JOIN ParticipantAudition PA on P.ID = PA.ParticipantID\n" +
//                "  INNER JOIN Audition A on PA.AuditionID = A.ID\n" +
//                "  WHERE P.ID = ?";
//        List<AuditionDTO> auditionDTOList = new ArrayList<>();
//        Connection connection = databaseConnection.getConnection();
//        try (PreparedStatement pstmt = connection.prepareStatement(querry)) {
//            pstmt.setInt(1, id);
//            try (ResultSet rs = pstmt.executeQuery()) {
//                while (rs.next()) {
//                    auditionDTOList.add(new AuditionDTO(rs.getInt("ID"), Types.valueOf(rs.getString("Type")), Distance.valueOf(rs.getString("Distance"))));
//                    ParticipantDTO object = new ParticipantDTO(rs.getInt("ID"), rs.getString("Name"), rs.getInt("Age"), null);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return auditionDTOList;
//    }
//
//        @Override
//    public ParticipantDTO findOne(int id) {
//        String querry = "SELECT * FROM Participant WHERE ID = ?";
//        Connection connection = databaseConnection.getConnection();
//        try (PreparedStatement pstmt = connection.prepareStatement(querry)) {
//            pstmt.setInt(1, id);
//            try (ResultSet rs = pstmt.executeQuery()) {
//                if (rs.next()) {
//                    ParticipantDTO object = new ParticipantDTO(rs.getInt("ID"), rs.getString("Name"), rs.getInt("Age"), findAuditionForOneParticipant(id));
//                    return object;
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public List<ParticipantDTO> findAll() {
//        String querryPatricipant = "SELECT DISTINCT *\n" +
//                "FROM Participant P";
//        String querryParticipantAudition = "";
//        List<ParticipantDTO> participantDTOList = new ArrayList<>();
//        Connection connection = databaseConnection.getConnection();
//        try (PreparedStatement pstmt = connection.prepareStatement(querryPatricipant)) {
//            try (ResultSet rs = pstmt.executeQuery()) {
//                while (rs.next()) {
//                    participantDTOList.add(new ParticipantDTO(rs.getInt("ID"), rs.getString("Name"), rs.getInt("Age"), findAuditionForOneParticipant(rs.getInt("ID"))));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return participantDTOList;
//    }
//
//    public void searchParticipants(List<ParticipantDTO> participantDTOList) {
//        participantDTOList = (List<ParticipantDTO>) findAll();
//    }
//
////    public static void main(String[] args) {
////        DatabaseConnection db = new DatabaseConnection(DatabaseConnection.getPropreties());
////        Connection connection2 =  db.getConnection();
////
////        String querryPatricipant = "SELECT DISTINCT *\n" +
////                "FROM ParticipantDTO P";
////        String querryParticipantAudition = "";
////        List<ParticipantDTO> participantDTOList = new ArrayList<>();
////        System.out.println("Connected@22");
////        try (PreparedStatement pstmt = connection2.prepareStatement(querryPatricipant)) {
////            try (ResultSet rs = pstmt.executeQuery()) {
////                while (rs.next()) {
////                    participantDTOList.add(new ParticipantDTO(rs.getInt("ID"), rs.getString("Name"), rs.getInt("Age"), null   ));
////                }
////            }
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////
////        for (ParticipantDTO u : participantDTOList){
////            System.out.println(u);
////        }
////    }
//}
