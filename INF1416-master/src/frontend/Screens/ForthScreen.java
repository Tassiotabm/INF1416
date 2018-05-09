package frontend.Screens;
import javax.swing.*;

import backend.QueryController.IQueryController;
import java.awt.Color;
import java.awt.Font;


public class ForthScreen  extends JFrame {

    @SuppressWarnings("unused")
    private static IQueryController query;
    private Font titleFont = new Font("Monospaced", Font.BOLD, 30);
    private static JPanel panel;

    public ForthScreen(IQueryController _query) {
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
        //this.path_completo.setPreferredSize(new Dimension(100, 100));
                this.setLayout(null);
        this.setVisible(true);

    }
}
