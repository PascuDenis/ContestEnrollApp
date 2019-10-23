package repository.databaseRepository;

import model.participant.AuditionDTO;
import model.participant.Distance;
import model.participant.ParticipantDTO;
import model.participant.Types;
import model.server.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.ICrudRepository;
import repository.IParticipantRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantDBRepository implements IParticipantRepository {
    DatabaseConnection databaseConnection;
    //private static final Logger logger = LogManager.getLogger();

    public ParticipantDBRepository(Properties props) {
        databaseConnection = new DatabaseConnection(props);
    }


    @Override
    public void save(ParticipantDTO entity) {
        String querryParticipant = "INSERT INTO Participant (Name, AGE) VALUES (?,?)";
        //logger.traceEntry("saving partipant {} ", entity);
        Connection connection = databaseConnection.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(querryParticipant)) {
            pstmt.setString(1, entity.getName());
            pstmt.setInt(2, entity.getAge());
            pstmt.execute();
        } catch (SQLException e) {
            //logger.error(e);
            System.out.println("Error saving DB " + e);
        }
//        logger.traceExit();
    }

    @Override
    public void delete(Integer id) {
        String querryParticipant = "DELETE FROM Participant WHERE ID = ?";
//        logger.traceEntry("deleting participant with id {}", id);
        Connection connection = databaseConnection.getConnection();
        try (PreparedStatement pstmt1 = connection.prepareStatement(querryParticipant)) {
            pstmt1.setInt(1, id);
            pstmt1.execute();
        } catch (SQLException ex) {
//            logger.error(ex);
            System.out.println("Error deleting DB " + ex);
        }
    }

    @Override
    public void update(ParticipantDTO entity) {
        String querryParticipant = "UPDATE Participant SET Name= ?, " +
                "Age= ? " +
                "WHERE ID= ?;";
//        logger.traceEntry("updatind participant{} ", entity);

        Connection connection = databaseConnection.getConnection();
        try (PreparedStatement pstmt1 = connection.prepareStatement(querryParticipant)) {
            pstmt1.setInt(3, entity.getId());
            pstmt1.setString(1, entity.getName());
            pstmt1.setInt(2, entity.getAge());
            pstmt1.execute();
        } catch (SQLException ex) {
//            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public ParticipantDTO findOne(Integer id) {
        String querry = "SELECT * FROM Participant WHERE ID = ?";
        Connection connection = databaseConnection.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(querry)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new ParticipantDTO(rs.getInt("ID"), rs.getString("Name"), rs.getInt("Age"), new ArrayList<>());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ParticipantDTO> findAll() {
        String querryPatricipant = "SELECT DISTINCT * FROM Participant P";
        List<ParticipantDTO> participantDTOList = new ArrayList<>();
        Connection connection = databaseConnection.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(querryPatricipant)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    participantDTOList.add(new ParticipantDTO(rs.getInt("ID"), rs.getString("Name"), rs.getInt("Age"), new ArrayList<>()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participantDTOList;
    }

    @Override
    public List<AuditionDTO> getAuditionsForOneParticipant(Integer id){
        String querry = "SELECT A.ID, A.Type, A.Distance FROM ParticipantAudition PA " +
                "INNER JOIN Audition A " +
                "ON PA.AuditionID = A.ID AND PA.ParticipantID = ?";
        List<AuditionDTO> auditionDTOList = new ArrayList<>();
        Connection connection = databaseConnection.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(querry)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()){
                while (rs.next()){
                    auditionDTOList.add(new AuditionDTO(rs.getInt("ID"), Types.valueOf(rs.getString("Type")), Distance.valueOf(rs.getString("Distance"))));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auditionDTOList;
    }

    @Override
    public Integer countNumberOfParticipantForOneAudition(AuditionDTO audition){
        int auditionId = audition.getId();
        String querry = "SELECT COUNT(*) as Number FROM ParticipantAudition PA " +
                "WHERE PA.AuditionID = ?";
        Connection connection = databaseConnection.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(querry)) {
            pstmt.setInt(1, auditionId);
            try (ResultSet rs = pstmt.executeQuery()){
                if (rs.next()){
                    return rs.getInt("Number");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getIndexOfLastParticipant(){
        String querry = "SELECT P.ID as Number FROM Participant P ORDER BY P.ID DESC";
        Connection connection = databaseConnection.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(querry)){
            try (ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()){
                    return rs.getInt("Number");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
