package frontend.Screens;
import java.awt.GridLayout;
import javax.swing.JFrame;
import backend.QueryController.IQueryController;

public class SecondScreen {

	@SuppressWarnings("unused")
	private static IQueryController query;
	private static JFrame loginframe = new JFrame();
	
	public SecondScreen(IQueryController _query) {
		query = _query;
		
		loginframe.setLayout(new GridLayout(2,1,0,0));
		loginframe.setLocation(512, 250);
		loginframe.setTitle("Projeto de Segurança");
		loginframe.setSize(500, 500);
		loginframe.setResizable(false);
		loginframe.setVisible(true);
		loginframe.setLocationRelativeTo(null);

	}

}
