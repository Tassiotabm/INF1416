package frontend.Screens;

import javax.swing.*;
import backend.QueryController.IQueryController;
import frontend.InterfaceController;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SecondScreen extends JFrame implements ActionListener {

    @SuppressWarnings("unused")
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
        initializeFonemas();

        this.setLocation(512, 250);
        this.setTitle("Projeto de Segurança");
        this.setSize(500, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setBackground(Color.WHITE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        this.panel.setBounds(0, 0, 500, 500);
        this.panel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Digite sua senha");
        lblTitle.setBounds(105, -230, 500, 500);
        lblTitle.setFont(titleFont);
        this.panel.add(lblTitle);

        ArrayList<String> btnRandomFonemas = generateRandomFonemas();

        btn1 = new JButton(btnRandomFonemas.get(0));
        btn1.setBounds(15, 100, 100, 50);
        btn1.addActionListener(this);
        this.panel.add(btn1);

        btn2 = new JButton(btnRandomFonemas.get(1));
        btn2.setBounds(380, 100, 100, 50);
        btn2.addActionListener(this);
        this.panel.add(btn2);

        btn3 = new JButton(btnRandomFonemas.get(2));
        btn3.setBounds(15, 240, 100, 50);
        btn3.addActionListener(this);
        this.panel.add(btn3);

        btn4 = new JButton(btnRandomFonemas.get(3));
        btn4.setBounds(380, 240, 100, 50);
        btn4.addActionListener(this);
        this.panel.add(btn4);

        btn5 = new JButton(btnRandomFonemas.get(4));
        btn5.setBounds(200, 380, 100, 50);
        btn5.addActionListener(this);
        this.panel.add(btn5);

        this.panel.setLayout(null);
        this.panel.setVisible(true);
        this.getContentPane().add(panel);
        this.setLayout(null);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == btn1) {
            //Chama método da fachada que chama método que guarda informações numa matriz no modelo
            System.out.println(btn1.getText());
            this.dispose();
            InterfaceController.startSecondScreen();
        } else if (ae.getSource() == btn2) {
            //Chama método da fachada que chama método que guarda informações numa matriz no modelo
            System.out.println(btn2.getText());
            this.dispose();
            InterfaceController.startSecondScreen();
        } else if (ae.getSource() == btn3) {
            //Chama método da fachada que chama método que guarda informações numa matriz no modelo
            System.out.println(btn3.getText());
            this.dispose();
            InterfaceController.startSecondScreen();
        } else if (ae.getSource() == btn4) {
            //Chama método da fachada que chama método que guarda informações numa matriz no modelo
            System.out.println(btn4.getText());
            this.dispose();
            InterfaceController.startSecondScreen();
        } else if (ae.getSource() == btn5) {
            //Chama método da fachada que chama método que guarda informações numa matriz no modelo
            System.out.println(btn5.getText());
            this.dispose();
            InterfaceController.startSecondScreen();
        } else {
            //Erro inesperado!
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
