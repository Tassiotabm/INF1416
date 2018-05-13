package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import backend.QueryController.QueryController;

public class StartConnection {

	private static Connection connection;
	private static QueryController controller;

	public StartConnection() {
		
	    try {
		    // load the sqlite-JDBC driver using the current class loader
			Class.forName("org.sqlite.JDBC");			
		     // create a database connection
			// C:\Users\tassi\eclipse-workspace\TrabLab_3
			connection = DriverManager.getConnection("jdbc:sqlite:segurancaDB");
			setController(new QueryController(connection));
			System.out.println("Conection success");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    
	}

	public static QueryController getController() {
		return controller;
	}

	public static void setController(QueryController controller) {
		StartConnection.controller = controller;
	}
	
}
