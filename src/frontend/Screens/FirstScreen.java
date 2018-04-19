package frontend.Screens;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import backend.QueryController.IQueryController;
import frontend.InterfaceController;

@SuppressWarnings("serial")
public class FirstScreen extends JFrame implements ActionListener{
	
	private String login;
	private static JFrame loginframe = new JFrame();
	private JButton logarButton = new JButton("Logar");
	private JTextField loginField = new JTextField();
	private static IQueryController query;
		
	public FirstScreen(IQueryController _query){
		query = _query;
		
		loginframe.setLayout(new GridLayout(2,1,0,0));
		loginframe.setLocation(512, 250);
		loginframe.setTitle("Projeto de Segurança");
		loginframe.setSize(300, 100);
		// Configurar os botoões
		logarButton.addActionListener(this);
		// Configurar o recebimento do login e senha

		// Setar as bordas dos textos e botoes e tamanhi

		loginField.setPreferredSize(new Dimension(50,50));

		// Ordem dos "blocos" dentro da tela de login
		loginframe.add(loginField,BorderLayout.NORTH);
		loginframe.add(logarButton,BorderLayout.SOUTH);
		// Setar ele visivel
		loginframe.setResizable(false);
		loginframe.setVisible(true);
		loginframe.setLocationRelativeTo(null);
		loginframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
		
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == logarButton){
			
			login = loginField.getText();
			
			try {
				if(query.findUser(login) == true) {
					loginframe.setVisible(false);		
					InterfaceController.startSecondScreen();
				}
				else
			        JOptionPane.showMessageDialog(null, "Login Incorreto",
			        		"Acess denied", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Não foi possível conectar ao Banco, verificar o acesso.",
		        		"Error 404", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else
			System.exit(0);
	}

}