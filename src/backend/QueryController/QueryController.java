package backend.QueryController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.UUID;
import java.util.Vector;
import Model.Usuario;
import backend.CertificateController;
import backend.KeyAuthentication;
import backend.PasswordAuthentication;
import frontend.AuthenticationUser;

public final class QueryController implements IQueryController{

	private static Vector <PreparedStatement> vetordeStatement = new Vector<PreparedStatement>();
	private Connection connection;
	private KeyAuthentication auth;
	private CertificateController certificate;
	
	public QueryController(Connection _connection) {
		this.connection = _connection;
		this.prepareStatments();
        this.auth = new KeyAuthentication();
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
		   	  	PreparedStatement statement_1 = vetordeStatement.get(11);
		   	  	statement_1.setString(1,login);
		   	  	statement_1.setQueryTimeout(30);  // set timeout to 30 sec.
			    rs = statement_1.executeQuery();
			    AuthenticationUser.setLogin(rs.getString(3));
			    AuthenticationUser.setGroup(rs.getString(6));
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
	
	public boolean checkCertificate(String privateKeyPath, String secretKey, String login) {
		try {
	 	    ResultSet rs = null;
	   	  	PreparedStatement statement = vetordeStatement.get(6);
	    	statement.setString(1,login);
		    statement.setQueryTimeout(30);  // set timeout to 30 sec.
		    
		    rs = statement.executeQuery();
		    
		    String certificatePath = rs.getString(1);
	        this.certificate = new CertificateController(certificatePath);
	        
	        PrivateKey privateKey = auth.decryptPrivateKey(secretKey,privateKeyPath);
	        if(auth.isSign(privateKey, this.certificate.getPublicKey())) {
	        	return true;
	        }
	        return false;
		}catch(Exception ex) {
			System.out.println(ex);
			return false;
		}
	}
	
	public boolean registerUser(Usuario user, String login) {
		try {
			System.out.println("Email recebido para query:"+user);
	 	    
	   	  	PreparedStatement statement = vetordeStatement.get(7);
	   	  	
	   	  	//O nome do usuário e o login name devem ser extraídos do campo de Sujeito do certificado
	   	  	
	        UUID uuid = UUID.randomUUID();
	        String randomUUIDString = uuid.toString();
	        Random rand = new Random();
	   	  	Integer salt = rand.nextInt(999999999);
	   	  	String hashedPass = PasswordAuthentication.generatePasswordHash(user.getSenha(), salt);
	   	  	String grupo = user.getGrupo();
	   	  	
	    	statement.setString(1,randomUUIDString); // ID
	    	statement.setString(2,hashedPass); // HashedPassword
	    	statement.setString(3,login);  // Tem que pegar do certificado!
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
	
	public boolean editUser(Usuario user,String login) {
		try {	 	    
	   	  	PreparedStatement statement = vetordeStatement.get(8);
	   	  	
	        Random rand = new Random();
	   	  	Integer salt = rand.nextInt(999999999);
	   	  	String hashedPass = PasswordAuthentication.generatePasswordHash(user.getSenha(), salt);
	   	  	
	    	statement.setString(2,hashedPass); // HashedPassword
	    	statement.setInt(1,salt); // PasswordKey
	    	statement.setString(3,user.getCaminhoCertificado()); // Certificate 
	    	statement.setString(4,login); //user login
	    	
		    statement.setQueryTimeout(30);  // set timeout to 30 sec.
		    statement.executeUpdate();
		    
		    return true;
	    	
		}catch(Exception ex) {
			System.out.println(ex);
			return false;
		}
	}
	
	public int GetUsersCount() {
		try {
	 	    ResultSet rs = null;
	   	  	PreparedStatement statement = vetordeStatement.get(5);
		    statement.setQueryTimeout(30);  // set timeout to 30 sec.
		    rs = statement.executeQuery();
		    
		    return rs.getInt(1);
	    	
		}catch(Exception ex) {
			System.out.println(ex);
			return 0;
		}
	}

	public void RegisterLog(String login,String filePath, int messageId) {
		try {
	   	  	PreparedStatement statement = vetordeStatement.get(9);
	   	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   	    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	   	    String ts = sdf.format(timestamp);
	        UUID uuid = UUID.randomUUID();
	        String randomUUIDString = uuid.toString();
	   	 	statement.setString(1,randomUUIDString); // ID
	    	statement.setString(2,ts); // ID
	    	statement.setString(3,login);
	    	statement.setString(4,filePath);
	    	statement.setInt(5,messageId);
		    statement.setQueryTimeout(30); 
		    statement.executeUpdate();
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			vetordeStatement.add(connection.prepareStatement("select count(*) from User"));
			//6
			vetordeStatement.add(connection.prepareStatement("select Certificate from User where LOGIN=?"));
			//7
			vetordeStatement.add(connection.prepareStatement("INSERT INTO User VALUES (?,?,?,?,?,?)"));
			//8
			vetordeStatement.add(connection.prepareStatement("UPDATE  User SET Password_Key=?,Hashed_Password=?,Certificate=?"
					+ "Where LOGIN=?"));
			//9
			vetordeStatement.add(connection.prepareStatement("INSERT INTO Logs VALUES (?,?,?,?,?)"));
			//10
			vetordeStatement.add(connection.prepareStatement("select * from Logs Order by Hora"));
			//11
			vetordeStatement.add(connection.prepareStatement("select * from User where LOGIN=?"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}