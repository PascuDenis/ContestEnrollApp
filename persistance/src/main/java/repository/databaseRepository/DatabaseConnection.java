package repository.databaseRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnection {
//    private final static String connectionSQLiteURL = "jdbc:sqlite:E:\\CS UBB\\Semestrul 4\\Medii de proiectare si programare\\DB\\lab2DB.db";
    private final static String connectionSQLiteURL = "jdbc:sqlite:E:/CS UBB/Semestrul 4/Medii de proiectare si programare/DB/lab2DB_TEST.db";
//    private static final Logger logger = LogManager.getLogger();
    private static Connection connection = null;
    private static Statement stmt = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private Properties jdbcProps;

    public DatabaseConnection(Properties jdbcProps) {
        this.jdbcProps = jdbcProps;
    }

    private Connection getNewConnection() {
        String driver = jdbcProps.getProperty("jdbc.driver");
        String url = jdbcProps.getProperty("jdbc.url");
        System.out.println("------------" + driver + "                                                          ");
        System.out.println("------------" + url  + "                                                          ");
//        logger.traceEntry();
//        logger.info("trying to connect to database ... {}", url);
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Error getting connection " + e);
        }
        return connection;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed())
                connection = getNewConnection();

        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
        return connection;
    }

    public static void setConnection(Connection connection) {
        DatabaseConnection.connection = connection;
    }

    public static Statement getStmt() {
        return stmt;
    }

    public static void setStmt(Statement stmt) {
        DatabaseConnection.stmt = stmt;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public void setPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }


    public static Properties getPropreties(){
        Properties serverProps=new Properties();
        try {
            serverProps.load(new FileReader("E:\\CS UBB\\Semestrul 4\\Medii de proiectare si programare\\Laboratoare\\LaboratorMPP\\db2.config"));
            System.out.println("Properties set. ");
            serverProps.list(System.out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Cannot find tempDBDriver.propreties "+e);
            return null;
        }
        return serverProps;
    }
//
//    public static void connect(String connectionSQLiteURL) {
//        try {
//            Class.forName(driver);
//            logger.info("Loaded driver ... {} - org.sqlite.JDBC");
//            connection = DriverManager.getConnection(url);
//        } catch (ClassNotFoundException e) {
//            logger.error(e);
//            System.out.println("Error loading driver " + e);
//        } catch (SQLException e) {
//            logger.error(e);
//            System.out.println("Error getting connection " + e);
//        }
//        System.out.println("Connection opened!");
//    }

    public static void deconect() {
        try {
            connection.close();
            System.out.println("conn closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//
//    public static void main(String[] args) {
//        DatabaseConnection db = new DatabaseConnection(DatabaseConnection.getPropreties());
//        Connection connection2 =  db.getConnection();
//
//        String querry = "SELECT * FROM UserDTO";
//        List<UserDTO> users = new ArrayList<>();
//        try (PreparedStatement pstmt = connection2.prepareStatement(querry)){
//            try(ResultSet rs = pstmt.executeQuery()){
//                while (rs.next()){
//                    users.add(new UserDTO(rs.getString("Username"), rs.getString("Password")));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        for (UserDTO u : users){
//            System.out.println(u);
//        }
//    }

}
