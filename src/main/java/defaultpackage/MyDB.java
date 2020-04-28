package defaultpackage;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class MyDB {
	public static Connection getDBConnection() {
		Connection dbConnection = null;
		
		try {
			Properties dbProperties = new Properties();
			
			FileReader dbPropertiesFile = new FileReader("C:\\Users\\harih\\Desktop\\Servlet workspace\\book-management-application\\src\\main\\java\\config\\db.properties");
			dbProperties.load(dbPropertiesFile);
			
			
			Class.forName("com.mysql.jdbc.Driver");
			
			dbConnection = DriverManager.getConnection(dbProperties.getProperty("DB_CONNECTION_STRING"),
					dbProperties.getProperty("DB_USERNAME"), dbProperties.getProperty("DB_PASSWORD"));
			
			System.out.println("DB connected");
			
			
		} catch(Exception exc) {
			System.out.println("DB connection failed");
			exc.printStackTrace();
			System.exit(-1);
		
		}
		return dbConnection;
	}
}
