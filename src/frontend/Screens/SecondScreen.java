package frontend.Screens;

import javax.swing.*;
import backend.QueryController.IQueryController;
import frontend.AuthenticationUser;
import frontend.InterfaceController;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SecondScreen extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String[][] matrix = new String[3][3];
	private static int clickCount = 0;
	
    private static IQueryController query;
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private static JPanel panel;
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JButton btn4;
    private JButton btn5;
    private ArrayList<String> fonemas = new ArrayList<String>();

    public SecondScreen(IQueryController _query) {
        super();
        
        query = _query;
        query.RegisterLog(AuthenticationUser.getLogin(), null , 3001);

        initializeFonemas();

        this.setLocation(512, 250);
        this.setTitle("Projeto de Segurança");
        this.setSize(500, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setBackground(Color.WHITE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        SecondScreen.panel.setBounds(0, 0, 500, 500);
        SecondScreen.panel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Digite sua senha");
        lblTitle.setBounds(105, -230, 500, 500);
        lblTitle.setFont(titleFont);
        SecondScreen.panel.add(lblTitle);

        ArrayList<String> btnRandomFonemas = generateRandomFonemas();

        btn1 = new JButton(btnRandomFonemas.get(0));
        btn1.setBounds(15, 100, 100, 50);
        btn1.addActionListener(this);
        SecondScreen.panel.add(btn1);

        btn2 = new JButton(btnRandomFonemas.get(1));
        btn2.setBounds(380, 100, 100, 50);
        btn2.addActionListener(this);
        SecondScreen.panel.add(btn2);

        btn3 = new JButton(btnRandomFonemas.get(2));
        btn3.setBounds(15, 240, 100, 50);
        btn3.addActionListener(this);
        SecondScreen.panel.add(btn3);

        btn4 = new JButton(btnRandomFonemas.get(3));
        btn4.setBounds(380, 240, 100, 50);
        btn4.addActionListener(this);
        SecondScreen.panel.add(btn4);

        btn5 = new JButton(btnRandomFonemas.get(4));
        btn5.setBounds(200, 380, 100, 50);
        btn5.addActionListener(this);
        SecondScreen.panel.add(btn5);

        SecondScreen.panel.setLayout(null);
        SecondScreen.panel.setVisible(true);
        this.getContentPane().add(panel);
        this.setLayout(null);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
    	
        if (ae.getSource() == btn1) {
        	SecondScreen.addToMatrix(btn1.getText());
            this.dispose();
            InterfaceController.startSecondScreen();
        } else if (ae.getSource() == btn2) {
        	SecondScreen.addToMatrix(btn2.getText());
            this.dispose();
            InterfaceController.startSecondScreen();
        } else if (ae.getSource() == btn3) {
        	SecondScreen.addToMatrix(btn3.getText());
            this.dispose();
            InterfaceController.startSecondScreen();
        } else if (ae.getSource() == btn4) {
        	SecondScreen.addToMatrix(btn4.getText());
            this.dispose();
            InterfaceController.startSecondScreen();
        } else if (ae.getSource() == btn5) {
        	SecondScreen.addToMatrix(btn5.getText());
            this.dispose();
            InterfaceController.startSecondScreen();
        } else {
        	
        }
    	
        SecondScreen.clickCount++;
        
        if(SecondScreen.clickCount == 3) {
        	SecondScreen.clickCount = 0;
        	if(query.validatePassword(AuthenticationUser.getLogin(),matrix)) {
                InterfaceController.startThirdScreen();
                query.RegisterLog(AuthenticationUser.getLogin(), null , 3002);
                query.RegisterLog(AuthenticationUser.getLogin(), null , 3003);

            } else {
                query.RegisterLog(AuthenticationUser.getLogin(), null , 4003);
                JOptionPane.showMessageDialog(null, "Senha incorreta",
                        "Acess denied", JOptionPane.INFORMATION_MESSAGE);
                
            }
        }

    }

    private void initializeFonemas() {
        fonemas.add("BA");
        fonemas.add("BE");
        fonemas.add("BO");
        fonemas.add("CA");
        fonemas.add("CE");
        fonemas.add("CO");
        fonemas.add("DA");
        fonemas.add("DE");
        fonemas.add("DO");
        fonemas.add("FA");
        fonemas.add("FE");
        fonemas.add("FO");
        fonemas.add("GA");
        fonemas.add("GE");
        fonemas.add("GO");
    }
    
    private static void addToMatrix(String fonema) {
    	String[] parts = fonema.split("-");

    	matrix [clickCount][0] = parts[0];
    	matrix [clickCount][1] = parts[1];
    	matrix [clickCount][2] = parts[2];
    	
    	for(int i = 0; i< matrix[clickCount].length ; i++ ) {
        	System.out.print(matrix[clickCount][i]);
    	}
    	System.out.println(" ");
    }

    private ArrayList<String> generateRandomFonemas() {
        ArrayList<String> res = new ArrayList<String>();

        for (int i = -1; i <= fonemas.size(); i++) {
            int random = (int) (Math.random() * (fonemas.size() - 1));
            res.add(fonemas.get(random) + "-");
            fonemas.remove(random);

            random = (int) (Math.random() * (fonemas.size() - 1));
            res.set(i + 1, res.get(i + 1) + fonemas.get(random) + "-");
            fonemas.remove(random);

            random = (int) (Math.random() * (fonemas.size() - 1));
            res.set(i + 1, res.get(i + 1) + fonemas.get(random));
            fonemas.remove(random);
        }
        return res;
    }

}
