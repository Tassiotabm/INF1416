package backend.QueryController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.Vector;

import backend.PasswordAuthentication;
import backend.Usuario;
import frontend.AuthenticationUser;

public final class QueryController implements IQueryController{

	private static Vector <PreparedStatement> vetordeStatement = new Vector<PreparedStatement>();
	private Connection connection;
	private UUID uuid;
	
	public QueryController(Connection _connection) {
		this.connection = _connection;
		this.prepareStatments();
        this.uuid = UUID.randomUUID();
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
	
	public boolean registerUser(Usuario user) {
		try {
			System.out.println("Email recebido para query:"+user);
	 	    
	   	  	PreparedStatement statement = vetordeStatement.get(7);
	   	  	
	   	  	//O nome do usuário e o login name devem ser extraídos do campo de Sujeito do certificado
	   	  	
	        String randomUUIDString = uuid.toString();
	   	  	
	    	statement.setString(1,randomUUIDString); // ID
	    	statement.setString(2,user.getSenha()); // HashedPassword
	    	statement.setString(3,user.getSenha()); // PasswordKey
	    	statement.setString(4,"Login"); // Login	
	    	statement.setString(6,"Name"); // Name
	    	statement.setString(7,user.getCaminhoCertificado()); // Certificate
	    	
		    statement.setQueryTimeout(30);  // set timeout to 30 sec.
		    statement.executeUpdate();
		    
		    return true;
	    	
		}catch(Exception ex) {
			System.out.println(ex);
			return false;
		}
	}
	
	public boolean editUser(Usuario user) {
		try {
			System.out.println("Email recebido para query:"+user);
	 	    
	   	  	PreparedStatement statement = vetordeStatement.get(8);
	   	  	
	    	statement.setString(1,user.getSenha()); // HashedPassword
	    	statement.setString(2,user.getSenha()); // PasswordKey
	    	statement.setString(3,user.getCaminhoCertificado()); // Certificate
	    	statement.setString(4,"login"); // 
	    	
		    statement.setQueryTimeout(30);  // set timeout to 30 sec.
		    statement.executeUpdate();
		    
		    return true;
	    	
		}catch(Exception ex) {
			System.out.println(ex);
			return false;
		}
	}
	
	public boolean checkCertificate(String certificatePath, String login) {
		try {
			System.out.println("Email recebido para query:"+login);
	 	    ResultSet rs = null;
	 	    
	   	  	PreparedStatement statement = vetordeStatement.get(1);
	    	statement.setString(1,login);
		    statement.setQueryTimeout(30);  // set timeout to 30 sec.
		    rs = statement.executeQuery();
		    if(!rs.getString(1).isEmpty()){
		    	//pegar o certificado com path
		    	//validar o certificado
		    	return true;
		   }else {
			   return false;
		   }		    	
		}catch(Exception ex) {
			System.out.println(ex);
			return false;
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
			//0
			vetordeStatement.add(connection.prepareStatement("SELECT CASE WHEN EXISTS (\r\n" + 
					"    SELECT *\r\n" + 
					"    FROM User\r\n" + 
					"    WHERE LOGIN = ?\r\n" + 
					")\r\n" + 
					"THEN CAST(1 AS BIT)\r\n" + 
					"ELSE CAST(0 AS BIT) END"));
			//1
			vetordeStatement.add(connection.prepareStatement("select Hashed_Password,Password_Key from User where LOGIN=?"));
			//2
			vetordeStatement.add(connection.prepareStatement("select * FROM Mensagens"));
			//3
			vetordeStatement.add(connection.prepareStatement("INSERT INTO Logs VALUES (?,?,?,?,?)"));
			//4
			vetordeStatement.add(connection.prepareStatement("select User_ID from User where LOGIN=?"));
			//5
			vetordeStatement.add(connection.prepareStatement("select User_ID from User where LOGIN=?"));
			//6
			vetordeStatement.add(connection.prepareStatement("select Certificate from User where LOGIN=?"));
			//7
			vetordeStatement.add(connection.prepareStatement("INSERT INTO User VALUES (?,?,?,?,?,?,?)"));
			//8
			vetordeStatement.add(connection.prepareStatement("UPDATE  User SET Password_Key=?,Hashed_Password=?,Certificate=?"
					+ "Where LOGIN=?"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}