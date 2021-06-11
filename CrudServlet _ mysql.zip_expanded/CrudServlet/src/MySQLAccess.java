import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class MySQLAccess {
	
  private Connection connect = null;
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null;

  final private String host = "localhost";
  final private String user = "root";
  final private String passwd = "";
  
  public void readDataBase() throws Exception {
    try {
      // This will load the MySQL driver, each DB has its own driver
      Class.forName("com.mysql.jdbc.Driver");
      
      // Setup the connection with the DB
      connect = DriverManager
          .getConnection("jdbc:mysql://" + host + "/bscit?"
              + "user=" + user + "&password=" + passwd );

      // Statements allow to issue SQL queries to the database
      statement = connect.createStatement();
      // Result set get the result of the SQL query
      resultSet = statement
          .executeQuery("select * from bscit.fyit");
      writeResultSet(resultSet);

    } catch (Exception e) {
      throw e;
    } finally {
      close();
    }

  }

  private void writeMetaData(ResultSet resultSet) throws SQLException {
    //   Now get some metadata from the database
    // Result set get the result of the SQL query
    
    System.out.println("The columns in the table are: ");
    
    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
    }
  }

  private void writeResultSet(ResultSet resultSet) throws SQLException {
    // ResultSet is initially before the first data set
    while (resultSet.next()) {
      // It is possible to get the columns via name
      // also possible to get the columns via the column number
      // which starts at 1
      // e.g. resultSet.getSTring(2);
      String name = resultSet.getString("name");
      //String age = resultSet.getString("age");
      String city = resultSet.getString("city");
      //Date date = resultSet.getDate("datum");
      //String comment = resultSet.getString("comments");
      System.out.println("User: " + name);
      System.out.println("City: " + city);
      //System.out.println("Summary: " + summary);
      //System.out.println("Date: " + date);
      //System.out.println("Comment: " + comment);
    }
  }

  // You need to close the resultSet
  private void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }

      if (statement != null) {
        statement.close();
      }

      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {

    }
  }

}