package backend.QueryController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public final class QueryController implements IQueryController{

	private static Vector <PreparedStatement> vetordeStatement = new Vector<PreparedStatement>();
	private Connection connection;
	
	public QueryController(Connection _connection) {
		this.connection = _connection;
		this.prepareStatments();
	}

	public boolean findUser(String email) {
		
		try {
			System.out.println("Email recebido para query:"+email);
	 	    ResultSet rs = null;
	 	    
	   	  	PreparedStatement statement = vetordeStatement.get(0);
	    	statement.setString(1,email);
		    statement.setQueryTimeout(30);  // set timeout to 30 sec.
		    rs = statement.executeQuery();
		    if(rs.getInt(1) == 1){		        	 	    
		    	return true;
		   }else {
			   return false;
		   }		    	
		}catch(Exception ex) {
			System.out.println(ex);
			return false;
		}
	}
	
	public void prepareStatments(){
		try {
			vetordeStatement.add(connection.prepareStatement("SELECT CASE WHEN EXISTS (\r\n" + 
					"    SELECT *\r\n" + 
					"    FROM User\r\n" + 
					"    WHERE email = ?\r\n" + 
					")\r\n" + 
					"THEN CAST(1 AS BIT)\r\n" + 
					"ELSE CAST(0 AS BIT) END"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

/*CREATE TABLE User(
   ID INT PRIMARY KEY     NOT NULL,
   EMAIL        CHAR(50)
);*/

/*INSERT INTO User (ID,EMAIL)
VALUES (1, 'teste@gmail.com');*/