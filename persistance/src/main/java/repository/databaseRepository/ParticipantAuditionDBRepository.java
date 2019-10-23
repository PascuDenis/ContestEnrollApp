package repository.databaseRepository;

import model.participant.Types;
import model.participant.Distance;
import model.participant.AuditionDTO;
import model.participant.ParticipantDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.IParticipantAuditionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantAuditionDBRepository implements IParticipantAuditionRepository {
    DatabaseConnection databaseConnection;
//    private static final Logger logger = LogManager.getLogger();

    public ParticipantAuditionDBRepository(Properties props) {
        databaseConnection = new DatabaseConnection(props);
    }

    @Override
    public void save(AuditionDTO entity) {

    }

    @Override
    public void save(Integer idParticipant, Integer idAudition) {
        String querryParticipantAudition = "INSERT INTO ParticipantAudition VALUES (?,?)";
//        logger.traceEntry("saving participantAudition {}  pID: ", idParticipant, "aID: " + idAudition);
        Connection connection = databaseConnection.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(querryParticipantAudition)) {
            pstmt.setInt(1, idParticipant);
            pstmt.setInt(2, idAudition);
            pstmt.execute();
        } catch (SQLException e) {
//            logger.error(e);
            System.out.println("Error DB " + e);
        }
    }
    @Override
    public void delete(Integer idParticipant) {
        Connection connection = databaseConnection.getConnection();
        String querry = String.format("DELETE FROM ParticipantAudition WHERE ParticipantID = %d", idParticipant);
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(querry);
        } catch (SQLException e) {
            System.out.println("Eroare la stergere " + e);
        }
    }

    @Override
    public void update(AuditionDTO entity) {

    }

    @Override
    public AuditionDTO findOne(Integer integer) {
        return null;
    }

    @Override
    public List<AuditionDTO> findAll() {
        return null;
    }


    public List<AuditionDTO> findAll(int idParticipant) {

        String querryPatricipant = String.format("SELECT A.ID, A.Type, A.Distance \n" +
                "FROM participantaudition PA INNER JOIN audition A \n" +
                "ON PA.AuditionID = A.ID \n" +
                "WHERE PA.ParticipantID = %d", idParticipant);

        List<AuditionDTO> auditionDTOList = new ArrayList<>();
        Connection connection = databaseConnection.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(querryPatricipant)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    auditionDTOList.add(new AuditionDTO(rs.getInt("ID"), Types.valueOf(rs.getString("Type")), Distance.valueOf(rs.getString("Distance"))));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auditionDTOList;
    }
}
