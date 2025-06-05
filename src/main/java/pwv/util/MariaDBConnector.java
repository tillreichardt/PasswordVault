package pwv.util;

import java.sql.*;
import java.util.ArrayList;

public class MariaDBConnector{
  private Connection connection;  
  private QueryResult currentQueryResult = null;
  private String message = null;

  public MariaDBConnector(String pIP, int pPort, String pDatabase, String pUsername, String pPassword){
    try {
        //Laden der Treiberklasse
        Class.forName("org.mariadb.jdbc.Driver");

        connection = DriverManager.getConnection("jdbc:mariadb://"+pIP+":"+pPort+"/"+pDatabase, pUsername, pPassword);

    } catch (Exception e) {
        message = e.getMessage();
    }
  }	

  public int executePrepared(String sql, Object... params) {
    currentQueryResult = null;
    message             = null;
    int generatedKey    = -1;

    // PreparedStatement mit RETURN_GENERATED_KEYS anlegen
    try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        // Parameter setzen
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }

        // Ausführen: execute() liefert true bei ResultSet, false bei Update Count
        boolean hasResultSet = ps.execute();

        if (hasResultSet) {
            // === SELECT‑Zweig: aktuelles QueryResult befüllen ===
            try (ResultSet rs = ps.getResultSet()) {
                ResultSetMetaData md = rs.getMetaData();
                int columnCount = md.getColumnCount();

                // Spaltennamen/-typen
                String[] names = new String[columnCount];
                String[] types = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    names[i] = md.getColumnLabel(i + 1);
                    types[i] = md.getColumnTypeName(i + 1);
                }

                // Zeilen sammeln
                ArrayList<String[]> rows = new ArrayList<>();
                while (rs.next()) {
                    String[] row = new String[columnCount];
                    for (int j = 0; j < columnCount; j++) {
                        row[j] = rs.getString(j + 1);
                    }
                    rows.add(row);
                }

                // in dein QueryResult packen
                String[][] data = rows.toArray(new String[0][0]);
                currentQueryResult = new QueryResult(data, names, types);
            }

        } else {
            // === DML‑Zweig: Generated Key auslesen ===
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    generatedKey = keys.getInt(1);
                }
            }
        }

    } catch (SQLException e) {
        message = e.getMessage();
    }

    return generatedKey;
}


  public Connection getConnection(){
    return connection;
  }

  public QueryResult getCurrentQueryResult(){
    return currentQueryResult;
  }

  public String getErrorMessage(){
    return message;
  }

  public void close(){
    try{
      connection.close();
    } catch (Exception e) {
      message = e.getMessage();
    }
  }
}