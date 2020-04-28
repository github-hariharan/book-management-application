package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import defaultpackage.MyDB;

@WebServlet ( urlPatterns = { "/deletebook" } ) 
public class DeleteServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		Connection dbConnectionObject = MyDB.getDBConnection();
		Integer bookid = Integer.parseInt(request.getParameter("bookid"));
		
		try {
			PreparedStatement preparedStatement = dbConnectionObject.prepareStatement("DELETE FROM book WHERE id = ?");
			preparedStatement.setInt(1, bookid);
			Integer recordUpdatedCount = preparedStatement.executeUpdate();
			if(recordUpdatedCount == 1) {
				response.getWriter().println("Book deleted");
			} else {
				response.getWriter().println("Book not found");
				
			}
				dbConnectionObject.close();
		} catch(SQLException sqlexc) {
			sqlexc.printStackTrace();
			System.exit(-1);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		
		
			
	}
	
}