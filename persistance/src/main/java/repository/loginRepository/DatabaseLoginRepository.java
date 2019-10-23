package repository.loginRepository;

import model.user.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.ILoginRepository;
import repository.databaseRepository.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DatabaseLoginRepository implements ILoginRepository {
    private DatabaseConnection databaseConnection;
    private List<UserDTO> userDTOList;
//    private static final Logger logger = LogManager.getLogger();


    public DatabaseLoginRepository(Properties props) {
//        logger.info("Initializing Repository.DatabaseRepository with propreties: {} ", props);
        this.databaseConnection = new DatabaseConnection(props);
    }

    @Override
    public void save(UserDTO entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(UserDTO entity) {

    }

    @Override
    public UserDTO findOne(Integer integer) {
        return null;
    }

    public List<UserDTO> findAll() {
        String querry = "SELECT * FROM User";
        List<UserDTO> userDTOS = new ArrayList<>();
        Connection connection = databaseConnection.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(querry)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    userDTOS.add(new UserDTO(rs.getString("Username"), rs.getString("Password")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userDTOS;
    }

    public UserDTO findOne(String username, String password) {
        List<UserDTO> userDTOList = findAll();
        UserDTO object = new UserDTO(username, password);
        for (UserDTO entity : userDTOList) {
            if (entity.equals(object)) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public boolean searchUser(UserDTO user) {
        Connection conn = databaseConnection.getConnection();
        String statement = String.format("SELECT * FROM User WHERE username = '%s' AND password = '%s' ",user.getUsername(),user.getPassword());
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(statement)) {
                if(rs.next()){
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Eroare la cautare" + e);
        }
        return false;
    }
    //        String querry = "SELECT * FROM Model.user.UserDTO U WHERE U.username = ? AND U.password = ?";
//        Connection connection = databaseConnection.getConnection();
//        try (PreparedStatement pstmt = connection.prepareStatement(querry)) {
//            pstmt.setString(1, username);
//            pstmt.setString(2, password);
//            try (ResultSet rs = pstmt.executeQuery()) {
//                if (rs.next()) {
//
//                }
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        public static void main(String[] args) {
//            DatabaseConnection db = new DatabaseConnection(DatabaseConnection.getPropreties());
//            Connection connection2 =  db.getConnection();
//
//            String querry = "SELECT * FROM UserDTO";
//            List<UserDTO> users = new ArrayList<>();
//            try (PreparedStatement pstmt = connection2.prepareStatement(querry)){
//                try(ResultSet rs = pstmt.executeQuery()){
//                    while (rs.next()){
//                        users.add(new UserDTO(rs.getString("Username"), rs.getString("Password")));
//                    }
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//            for (UserDTO u : users){
//                System.out.println(u);
//            }
//}
}
