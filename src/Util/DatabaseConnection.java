package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection {
  public static Connection connection;

  static{
    try {
      Properties properties = new Properties();
      var stream = DatabaseConnection.class.getClassLoader().getResourceAsStream("Util/database.properties");
      properties.load(stream);
      String driver = properties.getProperty("database.driver");
      String url = properties.getProperty("database.url");
      String username = properties.getProperty("database.username");
      String password = properties.getProperty("database.password");
      Class.forName(driver);
      connection = DriverManager.getConnection(url,username,password);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

}
