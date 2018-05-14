package backend.QueryController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;
import java.util.Vector;
import Model.Usuario;
import backend.PasswordAuthentication;

public final class QueryController implements IQueryController{

	private static Vector <PreparedStatement> vetordeStatement = new Vector<PreparedStatement>();
	private Connection connection;
	private UUID uuid;
	
	public QueryController(Connection _connection) {
		this.connection = _connection;
		this.prepareStatments();
        this.uuid = UUID.randomUUID();
	}

	public boolean findUser(String login) {
		
		try {
			System.out.println("Email recebido para query:"+login);
	 	    ResultSet rs = null;
	 	    
	   	  	PreparedStatement statement = vetordeStatement.get(0);
	    	statement.setString(1,login);
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
	
	public boolean validatePassword(String login, String [][] passMatrix) {
		try {
			System.out.println("Email recebido para query:"+login);
	 	    ResultSet rs = null;
	 	    
	   	  	PreparedStatement statement = vetordeStatement.get(1);
	    	statement.setString(1,login);
		    statement.setQueryTimeout(30);  // set timeout to 30 sec.
		    rs = statement.executeQuery();
		    if(!rs.getString(1).isEmpty()){		
		    	
		    	String hashedPwDb = rs.getString(1);
		    	int dbSalt = rs.getInt(2);
		    	
				for(int i =0; i<3;i++) {
					for(int j = 0; j<3;j++) {
						for(int z = 0; z<3; z++) {
							String password = passMatrix[0][i] + passMatrix[1][j]+ passMatrix[2][z];
					   	  	String hashedPass = PasswordAuthentication.generatePasswordHash(password, dbSalt);
					    	if(hashedPass.equals(hashedPwDb)) {
					    		return true;
					    	}
						}
					}
				}
		    	
		   	  	

		   }else {
			   return false;
		   }		    	
		}catch(Exception ex) {
			System.out.println(ex);
			return false;
		}
		return false;
	}
	
	public boolean checkCertificate(String certificatePath, String secretKey, String login) {
		try {
	 	    ResultSet rs = null;
	   	  	PreparedStatement statement = vetordeStatement.get(6);
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
	
	public boolean registerUser(Usuario user) {
		try {
			System.out.println("Email recebido para query:"+user);
	 	    
	   	  	PreparedStatement statement = vetordeStatement.get(7);
	   	  	
	   	  	//O nome do usuário e o login name devem ser extraídos do campo de Sujeito do certificado
	   	  	
	        String randomUUIDString = uuid.toString();
	        Random rand = new Random();
	   	  	Integer salt = rand.nextInt(999999999);
	   	  	String hashedPass = PasswordAuthentication.generatePasswordHash(user.getSenha(), salt);
	        String grupo = user.getGrupo();
	   	  	
	    	statement.setString(1,randomUUIDString); // ID
	    	statement.setString(2,hashedPass); // HashedPassword
	    	statement.setString(3,"Gmail");  // Tem que pegar do certificado!
	    	statement.setInt(4,salt); // PasswordKey
	    	statement.setString(5,user.getCaminhoCertificado()); // Certificate
	    	statement.setString(6,grupo);
	    	
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
			vetordeStatement.add(connection.prepareStatement("INSERT INTO User VALUES (?,?,?,?,?,?)"));
			//8
			vetordeStatement.add(connection.prepareStatement("UPDATE  User SET Password_Key=?,Hashed_Password=?,Certificate=?"
					+ "Where LOGIN=?"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}