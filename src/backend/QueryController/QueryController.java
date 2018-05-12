package backend.QueryController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import backend.PasswordAuthentication;
import frontend.AuthenticationUser;

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
	
	private String getUserHashedPassword(String email) {
		try {
			System.out.println("Email recebido para query:"+email);
	 	    ResultSet rs = null;
	 	    
	   	  	PreparedStatement statement = vetordeStatement.get(1);
	    	statement.setString(1,email);
		    statement.setQueryTimeout(30);  // set timeout to 30 sec.
		    rs = statement.executeQuery();
		    if(!rs.getString(1).isEmpty()){		        	 	    
		    	return rs.getString(1);
		   }else {
			   return null;
		   }		    	
		}catch(Exception ex) {
			System.out.println(ex);
			return null;
		}
	}
	
	public boolean checkPassword(String [][] matrix) {
		//			char [] password
		//        	String matrixString = matrix[1][0] + matrix[1][1]+ matrix[1][2];
    	//			char[] string = matrixString.toCharArray();
		String hashedPassword = this.getUserHashedPassword(AuthenticationUser.getEmail());
		
		if(hashedPassword.isEmpty())
			return false;
		
		for(int i =0; i<3;i++) {
			for(int j = 0; j<3;j++) {
				for(int z = 0; z<3; z++) {
					String matrixString = matrix[0][i] + matrix[1][j]+ matrix[2][z];
					char[] string = matrixString.toCharArray();
					/*if(PasswordAuthentication.gets(string, hashedPassword)) {
						System.out.println("HUE");
						return true;
					}*/	
				}
			}
		}
			
		return false;
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
			vetordeStatement.add(connection.prepareStatement("select HASH from User where EMAIL=?"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}