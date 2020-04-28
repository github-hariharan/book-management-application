package servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import defaultpackage.MyDB;

@WebServlet ( urlPatterns = { "/add" } )
public class AddServlet extends HttpServlet {
	Connection dbconnection = null;
	
	@Override
	public void init() {
		try {
			dbconnection = MyDB.getDBConnection();
			
		// Book table exist ?	
			DatabaseMetaData dbm = dbconnection.getMetaData();
		    ResultSet rs = dbm.getTables(null, null, "book", null);
		    if (rs.next()) {
		      System.out.println("Book table exist in db");
		    } else {
		      System.out.println("Book table not exist so creating now ..");
		      Statement statement = dbconnection.createStatement();
			  String createBookTableQuery = "CREATE TABLE book(id INTEGER NOT NULL AUTO_INCREMENT, name VARCHAR(255), authorname VARCHAR(255)," +
								  "price FLOAT, type VARCHAR(255), PRIMARY KEY(id))";
			  statement.executeUpdate(createBookTableQuery);
			  
			  
			  
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
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		Book newBook = new Book();
		newBook.setName(request.getParameter("bookname"));
		newBook.setAuthor(request.getParameter("bookauthor"));
		String bookPrice = request.getParameter("bookprice");
		newBook.setPrice(new Double(bookPrice));
		newBook.setType(request.getParameter("booktype"));
		
		// adding new book to database
		String query = "insert into book(name,authorname,price, type) values(?,?,?,?)";
		try {
			dbconnection = MyDB.getDBConnection();
			
			PreparedStatement preparedStatement = dbconnection.prepareStatement(query);
			preparedStatement.setString(1, newBook.getName());
			preparedStatement.setString(2, newBook.getAuthor());
			preparedStatement.setDouble(3, newBook.getPrice());
			preparedStatement.setString(4, newBook.getType());
			
			preparedStatement.executeUpdate();
			
			response.getWriter().println("Record added");
			dbconnection.close();
		} catch(Exception exc) {
			exc.printStackTrace();
		} 
	
	}
	
	
	
}