package carsharing.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    // JDBC driver name and database URL
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:." +
            FILE_SEPARATOR + "src" + FILE_SEPARATOR +
            "carsharing" + FILE_SEPARATOR + "db" +
            FILE_SEPARATOR;

    //  Database credentials
    private static final String USER = "";
    private static final String PASS = "";

    private String databaseFileName;

    public ConnectionFactory(String fileName) {
        this.databaseFileName = fileName;
    }

    public Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL +
                    this.databaseFileName, USER, PASS);
            conn.setAutoCommit(true);
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
