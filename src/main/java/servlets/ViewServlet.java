package servlets;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import defaultpackage.MyDB;

@WebServlet ( urlPatterns = { "/view" } ) 
public class ViewServlet extends HttpServlet {
	
	Connection dbconnection = null;
	
	@Override
	public void init() {
		try {
//				Properties dbProperties = new Properties();
//				FileReader dbPropertiesFile = new FileReader("./../config/db.properties");
//				dbProperties.load(dbPropertiesFile);
//				
//				
//				Class.forName("com.mysql.jdbc.Driver");
//				
//				dbconnection = DriverManager.getConnection(dbProperties.getProperty("DB_CONNECTION_STRING"),
//						dbProperties.getProperty("DB_USERNAME"), dbProperties.getProperty("DB_PASSWORD"));
//				System.out.println("Database connection status : success");
//			
		// checking for book table exist if it is not create
			dbconnection = MyDB.getDBConnection();
			
			DatabaseMetaData dbm = dbconnection.getMetaData();
		    ResultSet rs = dbm.getTables(null, null, "book", null);
		    if (rs.next()) {
		      System.out.println("Book table exist in db");
		    } else {
		    	throw new Exception("BOOK TABLE NOT EXIST");
		    }
		    
			
		} catch(SQLException sqlexc) {
			sqlexc.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				if(dbconnection != null) {
					dbconnection.close();
					System.out.println("database connection closed");
				}
				
			} catch(SQLException sqlexc) {
				sqlexc.printStackTrace();
			}
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response){
		try {
			response.setContentType("text/html");
			dbconnection = MyDB.getDBConnection();
			Statement statement = dbconnection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM book");
			
			PrintWriter out = response.getWriter();
			out.println("<html><body>");
			
			Book book = new Book();
			while(resultSet.next()) {
				book.setId(resultSet.getInt("id"));
				book.setName(resultSet.getString("name"));
				book.setAuthor(resultSet.getString("authorname"));
				book.setPrice(resultSet.getDouble("price"));
				book.setType(resultSet.getString("type"));
				out.println(book + "<br/>");
				
			}
			out.println("</body></html>");
			out.close();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(SQLException sqlexc) {
			sqlexc.printStackTrace();
		} finally {
			try {
			if(dbconnection != null) {
				dbconnection.close();
			}
			} catch(Exception exc) {
				exc.printStackTrace();
			}
			
		}
	}
	
}