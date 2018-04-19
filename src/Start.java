import backend.StartConnection;
import frontend.InterfaceController;

public class Start {

	static StartConnection connection = new StartConnection();
	
	public static void main(String[] args) {
		System.out.println("System Starting....");
		InterfaceController.init(StartConnection.getController());
	}

}
