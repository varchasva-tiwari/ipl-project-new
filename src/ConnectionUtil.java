import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static Connection getConnection() {
        Connection dbConnection = null;

        File iplDBFile = new File("Resources/IPL_db.Properties");

        try(FileReader fr = new FileReader(iplDBFile)) {
            Properties connectionProperties = new Properties();

            connectionProperties.load(fr);

            String url = connectionProperties.getProperty("url");
            String username = connectionProperties.getProperty("username");
            String password = connectionProperties.getProperty("password");

            dbConnection = DriverManager.getConnection(url, username, password);

        } catch(FileNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dbConnection;
    }
}
