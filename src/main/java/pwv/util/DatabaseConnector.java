package pwv.util;

import java.util.Properties;

public class DatabaseConnector {

    // connect with Database 
    Properties config;
    private MariaDBConnector dbConn;

    public DatabaseConnector(){
        config = DatabaseConfig.loadProperties();
        dbConn = new MariaDBConnector(
        config.getProperty("DB_HOST"),
        Integer.parseInt(config.getProperty("DB_PORT")),
        config.getProperty("DB_NAME"),
        config.getProperty("DB_USER"),
        config.getProperty("DB_PASSWORD")
        );
    }
  
    public void disconnect(){
        dbConn.close();
    }

    public String getErrorMessage(){
        return dbConn.getErrorMessage();
    }

    public MariaDBConnector getDbConn(){
        return dbConn;
    }
}