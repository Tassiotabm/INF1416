package frontend.Screens;

import javax.swing.*;
import javax.swing.table.TableCellEditor;

import Model.Usuario;
import backend.ModeloTabelaArquivosSecretos;
import backend.QueryController.IQueryController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class ForthScreen extends JFrame implements ActionListener {

	@SuppressWarnings("unused")
	private static IQueryController query;
	private Font fonteCabecalho = new Font("Monospaced", Font.BOLD, 15);
	private JPanel panel;
	private JButton btnCadastrar;
	private JButton btnArquivo;
	private JButton btnAlterar;
	private JButton btnConsultar;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JButton btnSair;
	private JButton btnOk;
	private JButton btnListar;

	private JLabel lblLogin;
	private JLabel lblGrupo;
	private JLabel lblNome;
	private JLabel lblQtd;
	private JTextField txtPath = new JTextField(40);
	private String[] listaGrupo = new String[] { "Administrador", "Usuário" };
	private JComboBox boxGrupo = new JComboBox(listaGrupo);
	// private JTextField txtGrupo = new JTextField(40);
	private JTextField txtPassword = new JTextField(40);
	private JTextField txtConfirmPassword = new JTextField(40);

	private String path = "", grupo = "", senha = "", confirmacao = "";
	private String login, gpo, nome;

	private int qtdUsuariosDoSistema;
	private int totalAcessosDoUsuario;
	private int totalConsultaDoUsuario;
	
	private ArrayList<String> fonemas = new ArrayList<String>();
	private ArrayList<JButton> tecladoSenha;
	private ArrayList<JButton> tecladoConfirma;

	private boolean flag_cadastrar_ou_alterar;
	private JTable tblArquivosSecretos;

	public ForthScreen(IQueryController _query, String login, String grupo, String nome, int qtdUsuariosDoSistema,
			int totalAcessosDoUsuario,int totalConsultaDoUsuario) {
		super();
		query = _query;
		initializeFonemas();

		this.login = login;
		this.gpo = grupo;
		this.nome = nome;
		this.qtdUsuariosDoSistema = qtdUsuariosDoSistema;
		this.totalAcessosDoUsuario = totalAcessosDoUsuario;
		this.totalConsultaDoUsuario = totalConsultaDoUsuario;
		
		this.setLocation(512, 250);
		this.setTitle("Projeto de Seguranca");
		this.setSize(500, 500);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setBackground(Color.WHITE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		tecladoSenha = gerarTecladoFonemas(true);
		tecladoConfirma = gerarTecladoFonemas(true);

		panel = new JPanel();
		this.panel.setBounds(0, 0, 500, 500);
		this.panel.setBackground(Color.white);

		lblLogin = new JLabel("Login: " + login);
		lblLogin.setBounds(15, -230, 500, 500);
		lblLogin.setFont(fonteCabecalho);
		this.panel.add(lblLogin);

		lblGrupo = new JLabel("Grupo: " + grupo);
		lblGrupo.setBounds(15, -212, 500, 500);
		lblGrupo.setFont(fonteCabecalho);
		this.panel.add(lblGrupo);

		lblNome = new JLabel("Nome: " + nome);
		lblNome.setBounds(15, -194, 500, 500);
		lblNome.setFont(fonteCabecalho);
		this.panel.add(lblNome);

		lblQtd = new JLabel("Total de usuários do sistema: " + qtdUsuariosDoSistema);
		lblQtd.setBounds(15, -150, 500, 500);
		this.panel.add(lblQtd);

		btnCadastrar = new JButton("Cadastrar um novo usuário");
		btnCadastrar.setBounds(70, 170, 350, 50);
		btnCadastrar.addActionListener(this);
		this.panel.add(btnCadastrar);

		btnAlterar = new JButton("Alterar senha pessoal e certificado digital do usuário");
		btnAlterar.setBounds(70, 240, 350, 50);
		btnAlterar.addActionListener(this);
		this.panel.add(btnAlterar);

		btnConsultar = new JButton("Consultar pasta de arquivos secretos do usuário");
		btnConsultar.setBounds(70, 310, 350, 50);
		btnConsultar.addActionListener(this);
		this.panel.add(btnConsultar);

		btnSair = new JButton("Sair do Sistema");
		btnSair.setBounds(70, 380, 350, 50);
		btnSair.addActionListener(this);
		this.panel.add(btnSair);

		this.txtPath.setPreferredSize(new Dimension(100, 100));
		this.boxGrupo.setPreferredSize(new Dimension(100, 100));

		this.panel.setLayout(null);
		this.panel.setVisible(true);
		this.getContentPane().add(panel);
		this.setLayout(null);
		this.setVisible(true);

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

	private ArrayList<JButton> gerarTecladoFonemas(boolean isSenha) {
		ArrayList<JButton> res = new ArrayList<JButton>();
		int x = 200, y;
		if (isSenha) {
			y = 225;
		} else {
			y = 330;
		}
		for (int i = 1; i <= fonemas.size(); i++) {
			res.add(new JButton());
			res.get(i - 1).setText(fonemas.get(i - 1));
			res.get(i - 1).addActionListener(this);
			res.get(i - 1).setBounds(x, y, 51, 25);
			if (i == 5 || i == 10) {
				y += 30;
				x = 200;
			} else {
				x += 56;
			}
		}
		return res;
	}

	public void preencherTabela(String sql) {
		ArrayList dados = new ArrayList();
		String[] colunas = new String[] { "NOME_CODIGO_DO_ARQUIVO", "NOME_SECRETO_DO_ARQUIVO", "	DONO_ARQUIVO",
				"GRUPO_ARQUIVO" };

		// conexao
		// consulta sql pela conexao armazenando em dados

		ModeloTabelaArquivosSecretos modelo = new ModeloTabelaArquivosSecretos(dados, colunas);
		tblArquivosSecretos.setModel(modelo);
		tblArquivosSecretos.getColumnModel().getColumn(0).setPreferredWidth(70);
		tblArquivosSecretos.getColumnModel().getColumn(0).setResizable(true);
		tblArquivosSecretos.getColumnModel().getColumn(1).setPreferredWidth(70);
		tblArquivosSecretos.getColumnModel().getColumn(1).setResizable(true);
		tblArquivosSecretos.getColumnModel().getColumn(2).setPreferredWidth(70);
		tblArquivosSecretos.getColumnModel().getColumn(2).setResizable(true);
		tblArquivosSecretos.getColumnModel().getColumn(3).setPreferredWidth(70);
		tblArquivosSecretos.getColumnModel().getColumn(3).setResizable(true);
		tblArquivosSecretos.getTableHeader().setReorderingAllowed(true);
		tblArquivosSecretos.setAutoResizeMode(tblArquivosSecretos.AUTO_RESIZE_OFF);
		tblArquivosSecretos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(tblArquivosSecretos);
		this.getContentPane().add(scroll);
		this.getContentPane().add(tblArquivosSecretos);
		tblArquivosSecretos.setVisible(true);
		// desconectar
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == btnCadastrar) {

			flag_cadastrar_ou_alterar = true;
			this.panel.remove(btnCadastrar);
			this.panel.remove(btnAlterar);
			this.panel.remove(btnConsultar);
			this.panel.remove(btnSair);

			this.panel.repaint();

			JLabel lblCertificado = new JLabel("Escolher Certificado: ");
			lblCertificado.setBounds(15, -105, 500, 500);
			this.panel.add(lblCertificado);

			txtPath.setBounds(200, 140, 200, 26);
			this.panel.add(txtPath, BorderLayout.NORTH);

			btnArquivo = new JButton("Arquivo");
			btnArquivo.setBounds(398, 140, 78, 25);
			btnArquivo.addActionListener(this);
			this.panel.add(btnArquivo);

			JLabel lblGrupo = new JLabel("Grupo: ");
			lblGrupo.setBounds(15, -60, 500, 500);
			this.panel.add(lblGrupo);

			boxGrupo.setBounds(200, 180, 275, 25);
			this.panel.add(boxGrupo, BorderLayout.NORTH);

			JLabel lblSenha = new JLabel("Senha Pessoal: ");
			lblSenha.setBounds(15, -15, 500, 500);
			this.panel.add(lblSenha);

			txtPassword.setPreferredSize(new Dimension(100, 100));
			txtPassword.setBounds(15, 255, 150, 25);
			txtPassword.setEditable(false);
			this.panel.add(txtPassword, BorderLayout.NORTH);

			for (JButton btn : tecladoSenha) {
				this.panel.add(btn);
			}

			tecladoConfirma = gerarTecladoFonemas(false);

			JLabel lblConfirma = new JLabel("Confirmar Senha Pessoal: ");
			lblConfirma.setBounds(15, 90, 500, 500);
			this.panel.add(lblConfirma);

			txtConfirmPassword.setPreferredSize(new Dimension(100, 100));
			txtConfirmPassword.setBounds(15, 360, 150, 25);
			txtConfirmPassword.setEditable(false);
			this.panel.add(txtConfirmPassword, BorderLayout.NORTH);

			for (JButton btn : tecladoConfirma) {
				this.panel.add(btn);
			}

			btnSalvar = new JButton("Salvar");
			btnSalvar.setBounds(150, 430, 90, 25);
			btnSalvar.addActionListener(this);
			this.panel.add(btnSalvar);

			btnCancelar = new JButton("Cancelar");
			btnCancelar.setBounds(250, 430, 90, 25);
			btnCancelar.addActionListener(this);
			this.panel.add(btnCancelar);

			this.panel.repaint();

		} else if (ae.getSource() == btnArquivo) {
			JFileChooser chooser = new JFileChooser();
			chooser.showOpenDialog(null);
			File f = chooser.getSelectedFile();
			String filename = f.getAbsolutePath();
			txtPath.setText(filename);
			this.path = filename;
			System.out.println(path);

		} else if (ae.getSource() == btnListar) {
			// implementar ainda

		} else if (ae.getSource() == btnSalvar) {
			path = txtPath.getText();
			int indice = boxGrupo.getSelectedIndex();
			grupo = listaGrupo[indice];
			System.out.println("Grupo: " + grupo);

			if (flag_cadastrar_ou_alterar
					&& (path.isEmpty() || grupo.isEmpty() || senha.isEmpty() || confirmacao.isEmpty())) {
				JOptionPane.showMessageDialog(this, "Preencha todos os campos do formulário para prosseguir.", // mensagem
						"Atenção!", // titulo da janela
						JOptionPane.INFORMATION_MESSAGE);

			} else if (!senha.equals(confirmacao)) {
				JOptionPane.showMessageDialog(this, "A senha não coincide com a confirmação. Tente novamente.", // mensagem
						"Atenção!", // titulo da janela
						JOptionPane.INFORMATION_MESSAGE);

				senha = "";
				confirmacao = "";
				txtPassword.setText("");
				txtConfirmPassword.setText("");
				this.panel.repaint();

			} else if (!senha.isEmpty() && (senha.substring(0, 2).equals(senha.substring(2, 4))
					|| senha.substring(2, 4).equals(senha.substring(4, 6)))) {

				JOptionPane.showMessageDialog(this, "Fonemas igual em sequencia não são permitidos. Tente novamente.", // mensagem
						"Atenção!", // titulo da janela
						JOptionPane.INFORMATION_MESSAGE);

				senha = "";
				confirmacao = "";
				txtPassword.setText("");
				txtConfirmPassword.setText("");
				this.panel.repaint();
			} else {
				// deve ser tirado do certificado
				String versao = "kkk", serie = "v2", validade = "21/03/2023", tipo = "iiiii", emissor = "Anderson",
						sujeito = "And", email = "and@gmail.com";

				String texto = "Confirmar Dados ?\n\n\n";
				texto += "Grupo: " + grupo + "\n";
				texto += "Caminho Certificado: " + path + "\n";
				texto += "Versão: " + versao + "\n";
				texto += "Série: " + serie + "\n";
				texto += "Validade: " + validade + "\n";
				texto += "Tipo de Assinatura: " + tipo + "\n";
				texto += "Emissor: " + emissor + "\n";
				texto += "Sujeito (Friendly Name): " + sujeito + "\n";
				texto += "E-mail: " + email + "\n";

				switch (JOptionPane.showConfirmDialog(null, texto)) {
				case 0:
					JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!", // mensagem
							"Sucesso!", // titulo da janela
							JOptionPane.INFORMATION_MESSAGE);

					this.dispose();
					new ForthScreen(query, login, gpo, nome, qtdUsuariosDoSistema, totalAcessosDoUsuario, totalConsultaDoUsuario);
					break;
				case 1:
					senha = "";
					confirmacao = "";
					txtPassword.setText("");
					txtConfirmPassword.setText("");
					this.panel.repaint();
					break;
				case 2:
					senha = "";
					confirmacao = "";
					txtPassword.setText("");
					txtConfirmPassword.setText("");
					this.panel.repaint();
					break;
				}
				Usuario user = new Usuario(grupo, path, senha);
				query.registerUser(user);
				// deveria enviar esse usuario para algum
				// lugar////////////////////////////////////////////////////////////////////////////////////////////////
			}
		} else if (ae.getSource() == btnCancelar) {
			this.dispose();
			new ForthScreen(query, login, gpo, nome, qtdUsuariosDoSistema, totalAcessosDoUsuario, totalConsultaDoUsuario);

		} else if (ae.getSource() == btnAlterar) {

			flag_cadastrar_ou_alterar = false;
			this.panel.remove(btnCadastrar);
			this.panel.remove(btnAlterar);
			this.panel.remove(btnConsultar);
			this.panel.remove(btnSair);
			this.panel.remove(lblQtd);

			this.panel.repaint();

			lblQtd = new JLabel("Total de acessos do usuário: " + totalAcessosDoUsuario);
			lblQtd.setBounds(15, -150, 500, 500);
			this.panel.add(lblQtd);

			JLabel lblCertificado = new JLabel("Escolher Certificado: ");
			lblCertificado.setBounds(15, -105, 500, 500);
			this.panel.add(lblCertificado);

			txtPath.setBounds(200, 140, 200, 26);
			txtPath.setText(path);
			this.panel.add(txtPath, BorderLayout.NORTH);

			btnArquivo = new JButton("Arquivo");
			btnArquivo.setBounds(398, 140, 78, 25);
			btnArquivo.addActionListener(this);
			this.panel.add(btnArquivo);

			JLabel lblSenha = new JLabel("Senha Pessoal: ");
			lblSenha.setBounds(15, -15, 500, 500);
			this.panel.add(lblSenha);

			txtPassword.setPreferredSize(new Dimension(100, 100));
			txtPassword.setBounds(15, 255, 150, 25);
			txtPassword.setEditable(false);
			this.panel.add(txtPassword, BorderLayout.NORTH);

			for (JButton btn : tecladoSenha) {
				this.panel.add(btn);
			}

			tecladoConfirma = gerarTecladoFonemas(false);

			JLabel lblConfirma = new JLabel("Confirmar Senha Pessoal: ");
			lblConfirma.setBounds(15, 90, 500, 500);
			this.panel.add(lblConfirma);

			txtConfirmPassword.setPreferredSize(new Dimension(100, 100));
			txtConfirmPassword.setBounds(15, 360, 150, 25);
			txtConfirmPassword.setEditable(false);
			this.panel.add(txtConfirmPassword, BorderLayout.NORTH);

			for (JButton btn : tecladoConfirma) {
				this.panel.add(btn);
			}

			btnSalvar = new JButton("Salvar");
			btnSalvar.setBounds(150, 430, 90, 25);
			btnSalvar.addActionListener(this);
			this.panel.add(btnSalvar);

			btnCancelar = new JButton("Cancelar");
			btnCancelar.setBounds(250, 430, 90, 25);
			btnCancelar.addActionListener(this);
			this.panel.add(btnCancelar);

			this.panel.repaint();

		} else if (ae.getSource() == btnConsultar) {

			this.panel.remove(btnCadastrar);
			this.panel.remove(btnAlterar);
			this.panel.remove(btnConsultar);
			this.panel.remove(btnSair);
			this.panel.remove(lblQtd);
			this.panel.remove(lblQtd);

			this.panel.repaint();

			lblQtd = new JLabel("Total de consultas do usuário: " + totalConsultaDoUsuario);
			lblQtd.setBounds(15, -150, 500, 500);
			this.panel.add(lblQtd);

			JLabel lblCertificado = new JLabel("Escolher da pasta: ");
			lblCertificado.setBounds(15, -105, 500, 500);
			this.panel.add(lblCertificado);

			txtPath.setBounds(200, 140, 200, 26);
			txtPath.setText(path);
			this.panel.add(txtPath, BorderLayout.NORTH);

			btnArquivo = new JButton("Arquivo");
			btnArquivo.setBounds(398, 140, 78, 25);
			btnArquivo.addActionListener(this);
			this.panel.add(btnArquivo);

			btnListar = new JButton("Listar");
			btnListar.setBounds(15, 190, 78, 25);
			btnListar.addActionListener(this);
			this.panel.add(btnListar);
			
			btnCancelar = new JButton("Cancelar");
			btnCancelar.setBounds(200, 430, 90, 25);
			btnCancelar.addActionListener(this);
			this.panel.add(btnCancelar);

			preencherTabela("Select * from XXXXX;");

		} else if (ae.getSource() == btnOk) {
			System.exit(0);

		} else if (ae.getSource() == btnSair) {

			this.panel.remove(btnCadastrar);
			this.panel.remove(btnAlterar);
			this.panel.remove(btnConsultar);
			this.panel.remove(btnSair);
			this.panel.remove(lblQtd);

			this.panel.repaint();

			JLabel lbl = new JLabel("Saída do Sistema");
			lbl.setFont(fonteCabecalho);
			lbl.setBounds(15, -150, 500, 500);
			this.panel.add(lbl);

			JLabel lbl2 = new JLabel("Você está prestes a sair do sistema, deseja sair?");
			lbl2.setBounds(15, -40, 500, 500);
			this.panel.add(lbl2);

			JLabel lbl3 = new JLabel("Clique OK para sair ou Cancelar para voltar ao menu.");
			lbl3.setBounds(15, -5, 500, 500);
			this.panel.add(lbl3);

			btnOk = new JButton("OK");
			btnOk.setBounds(150, 430, 90, 25);
			btnOk.addActionListener(this);
			this.panel.add(btnOk);

			btnCancelar = new JButton("Cancelar");
			btnCancelar.setBounds(250, 430, 90, 25);
			btnCancelar.addActionListener(this);
			this.panel.add(btnCancelar);

			this.panel.repaint();

		} else {
			for (JButton btn : tecladoSenha) {
				if (ae.getSource() == btn) {
					senha += btn.getText();
					txtPassword.setText(txtPassword.getText().concat("*"));
				}
			}
			System.out.println("Senha: " + senha);

			for (JButton btn : tecladoConfirma) {
				if (ae.getSource() == btn) {
					confirmacao += btn.getText();
					txtConfirmPassword.setText(txtConfirmPassword.getText().concat("*"));
				}
			}

			System.out.println("Confirma Senha: " + confirmacao);
		}
	}
}
