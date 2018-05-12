package frontend.Screens;

import javax.swing.*;

import backend.QueryController.IQueryController;
import frontend.InterfaceController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ThirdScreen extends JFrame implements ActionListener {

    @SuppressWarnings("unused")
    private static IQueryController query;
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private JPanel panel;
    private JButton btn1;
    private JButton btn2;
    private JTextField txtPath = new JTextField(40);
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

        JLabel lblTitle = new JLabel("Escolher Certificado");
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
        this.panel.add(btn2);

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
            System.out.println(btn2.getText());
            this.dispose();
            InterfaceController.startForthScreen();

        } else {
            //Erro inesperado!
        }
    }
}
