package frontend.Screens;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import backend.QueryController.IQueryController;
import frontend.AuthenticationUser;
import frontend.InterfaceController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ThirdScreen extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
    private static IQueryController query;
	private String filePath;
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private JPanel panel;
    private JButton btn1;
    private JButton btn2;
    private JTextField txtPath = new JTextField(40);
    private JTextField secret = new JTextField(40);
    private String path;

    public ThirdScreen(IQueryController _query) {
        super();
        query = _query;

        this.setLocation(512, 250);
        this.setTitle("Projeto de Seguranca");
        this.setSize(500, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setBackground(Color.WHITE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        this.panel.setBounds(0, 0, 500, 500);
        this.panel.setBackground(Color.white);

        //this.txtPath.setPreferredSize(new Dimension(100, 100));
        txtPath.setBounds(40, 98, 300, 50);
        this.panel.add(txtPath, BorderLayout.NORTH);

        JLabel lblTitle = new JLabel("Indique a assinatura");
        lblTitle.setBounds(105, -230, 500, 500);
        lblTitle.setFont(titleFont);
        this.panel.add(lblTitle);

        btn1 = new JButton("Arquivo");
        btn1.setBounds(380, 100, 100, 50);
        btn1.addActionListener(this);
        this.panel.add(btn1);

        btn2 = new JButton("PROX");
        btn2.setBounds(380, 200, 100, 50);
        btn2.addActionListener(this);
        btn2.setEnabled(false);
        this.panel.add(btn2);

        secret.setBounds(40,200, 300, 50);
        secret.addCaretListener(new visualizador());
        this.panel.add(secret, BorderLayout.NORTH);
        
        JLabel lblSecret = new JLabel("Pergunta Secreta");
        lblSecret.setBounds(105,-75, 500, 500);
        lblSecret.setFont(titleFont);
        this.panel.add(lblSecret);

        this.panel.setLayout(null);
        this.panel.setVisible(true);
        this.getContentPane().add(panel);
        this.setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
    	
    	
        if (ae.getSource() == btn1) {
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            File f = chooser.getSelectedFile();
            String filename = f.getAbsolutePath();
            txtPath.setText(filename);
            this.path = filename;
            System.out.println(path);
        } else if (ae.getSource() == btn2) {
        	
            if(query.checkCertificate(this.path, this.secret.getText(), AuthenticationUser.getLogin())) {  
                this.dispose();
            	InterfaceController.startForthScreen();
                query.RegisterLog(AuthenticationUser.getLogin(), null , 4003);
            }else {
                JOptionPane.showMessageDialog(null, "Assinatura ou chave secreta inválida",
                        "Acess denied", JOptionPane.INFORMATION_MESSAGE);
                query.RegisterLog(AuthenticationUser.getLogin(), null , 4006);
            }
            
        } else {
            //Erro inesperado!
        }
    }
    private class visualizador implements CaretListener{

    	@Override
    	public void caretUpdate(CaretEvent e) {
    		String s=secret.getText(),s2=txtPath.getText();
    		
    		if(s.isEmpty() || s2.isEmpty())
    			btn2.setEnabled(false);
    		else
    			btn2.setEnabled(true);
    		
    	}
    	
    }
}

