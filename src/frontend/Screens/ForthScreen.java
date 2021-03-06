package frontend.Screens;

import javax.swing.*;


import Model.Usuario;
import backend.CertificateController;
import backend.FolderController;
import backend.ModeloTabelaArquivosSecretos;
import backend.QueryController.IQueryController;
import frontend.AuthenticationUser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
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
	private JButton btnAcessarTodosOsArquivos;
	private boolean editFlag = false;
	private JLabel lblLogin;
	private JLabel lblGrupo;
	private JLabel lblNome;
	private JLabel lblQtd;
	private JTextField txtPath = new JTextField(40);
	private String[] listaGrupo = new String[] { "Administrador", "Usu�rio" };
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
	private JTable tblArquivosSecretos = new JTable();
	private boolean fileListExist = false;
	private String[] fullContentFromFolders = null;
	private FolderController folderController = null;
	
	public ForthScreen(IQueryController _query, String login, String grupo, String nome, int qtdUsuariosDoSistema,
			int totalAcessosDoUsuario,int totalConsultaDoUsuario) {
		super();
		query = _query;
		initializeFonemas();
		query.RegisterLog(AuthenticationUser.getLogin(), null , 5001);
		
		this.login = login;
		this.gpo = grupo;
		this.nome = nome;
		this.qtdUsuariosDoSistema = query.GetUsersCount();
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

		lblQtd = new JLabel("Total de usu�rios do sistema: " + query.GetUsersCount());
		lblQtd.setBounds(15, -150, 500, 500);
		this.panel.add(lblQtd);

		btnCadastrar = new JButton("Cadastrar um novo usu�rio");
		btnCadastrar.setBounds(70, 170, 350, 50);
		btnCadastrar.addActionListener(this);
		this.panel.add(btnCadastrar);

		btnAlterar = new JButton("Alterar senha pessoal e certificado digital do usu�rio");
		btnAlterar.setBounds(70, 240, 350, 50);
		btnAlterar.addActionListener(this);
		this.panel.add(btnAlterar);

		btnConsultar = new JButton("Consultar pasta de arquivos secretos do usu�rio");
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

	public void preencherTabela(String [] conteudoDoFolder) {
		ArrayList<String> dados = new ArrayList<String>(Arrays.asList(conteudoDoFolder));
		String[] colunas = new String[] { "NOME_CODIGO_DO_ARQUIVO", "NOME_SECRETO_DO_ARQUIVO", "	DONO_ARQUIVO",
				"GRUPO_ARQUIVO" };
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
		tblArquivosSecretos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
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

		if (ae.getSource() == btnCadastrar){
			editFlag = false;
			query.RegisterLog(AuthenticationUser.getLogin(), null , 5002);
			query.RegisterLog(AuthenticationUser.getLogin(), null , 6001);
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

		} else if(ae.getSource() == btnAcessarTodosOsArquivos) {
			
			for(String s: this.fullContentFromFolders) {
				if(!folderController.checkSecretFileAcess(s))
					System.err.println("Usuario atual n�o possui permiss�o para acessar o arquivo"+"\n"+ s);
				
				else if(!folderController.acessSecretFile(txtPath.getText(),s))
					System.out.println("N�o foi poss�vel acessar o arquivo especificado");
			}
		}
		
		else if (ae.getSource() == btnListar) {
			query.RegisterLog(AuthenticationUser.getLogin(), null , 8003);			
			this.folderController = new FolderController(
					AuthenticationUser.getCertificateController(),
					AuthenticationUser.getPrivateKey());
			
			if(folderController.checkSecretFolder(txtPath.getText())) {
				String content = new String(folderController.getSecretFolderContent());
				List<String> contentAsList = Arrays.asList(content.split("\n"));
				String[] fullContent = new String[contentAsList.size()];
				for(int i = 0; i < contentAsList.size(); i++) {
					fullContent[i] = contentAsList.get(i);
					System.out.println(fullContent[i]);
				}
				this.fullContentFromFolders = fullContent;
				this.fileListExist  = true;
				if(this.btnAcessarTodosOsArquivos != null)
					this.btnAcessarTodosOsArquivos.setEnabled(fileListExist);
				//filesList.setListData(contentAsVector);
				this.panel.repaint();
			} else {
				
				JOptionPane.showMessageDialog(this, "Folder has integrity and authenticity problems!",
						"Aten��o!", // titulo da janela
						JOptionPane.INFORMATION_MESSAGE);
				// Erro de acesso!
			}

		} else if (ae.getSource() == btnSalvar) {
			
			path = txtPath.getText();
			int indice = boxGrupo.getSelectedIndex();
			grupo = listaGrupo[indice];
			System.out.println("Grupo: " + grupo);
			if(flag_cadastrar_ou_alterar)
				query.RegisterLog(AuthenticationUser.getLogin(), null , 6002);
			else
				query.RegisterLog(AuthenticationUser.getLogin(), null , 7001);
			
			if (flag_cadastrar_ou_alterar
					&& (path.isEmpty() || grupo.isEmpty() || senha.isEmpty() || confirmacao.isEmpty())) {
				JOptionPane.showMessageDialog(this, "Preencha todos os campos do formul�rio para prosseguir.", // mensagem
						"Aten��o!", // titulo da janela
						JOptionPane.INFORMATION_MESSAGE);

			} else if (!senha.equals(confirmacao)) {
				JOptionPane.showMessageDialog(this, "A senha n�o coincide com a confirma��o. Tente novamente.", // mensagem
						"Aten��o!", // titulo da janela
						JOptionPane.INFORMATION_MESSAGE);

				senha = "";
				confirmacao = "";
				txtPassword.setText("");
				txtConfirmPassword.setText("");
				this.panel.repaint();

			} else if (!senha.isEmpty() && (senha.substring(0, 2).equals(senha.substring(2, 4))
					|| senha.substring(2, 4).equals(senha.substring(4, 6)))) {

				JOptionPane.showMessageDialog(this, "Fonemas igual em sequencia n�o s�o permitidos. Tente novamente.", // mensagem
						"Aten��o!", // titulo da janela
						JOptionPane.INFORMATION_MESSAGE);

				senha = "";
				confirmacao = "";
				txtPassword.setText("");
				txtConfirmPassword.setText("");
				this.panel.repaint();
			} else {
				// deve ser tirado do certificado
				Usuario user = new Usuario(grupo, path, senha);
				
		   	  	CertificateController certificadoRecebido = new CertificateController(path);
		   	  	//String login = certificadoRecebido.getLogin();

				String texto = "Confirmar Dados ?\n\n\n";
				texto += "Grupo: " + grupo + "\n";
				texto += "Caminho Certificado: " + path + "\n";
				texto += "Vers�o: " + certificadoRecebido.getVersion() + "\n";
				texto += "S�rie: " + certificadoRecebido.getSerial() + "\n";
				texto += "Validade: " + certificadoRecebido.getDate() + "\n";
				texto += "Tipo de Assinatura: " + certificadoRecebido.getType() + "\n";
				texto += "Emissor: " + certificadoRecebido.getEmissor() + "\n";
				texto += "Sujeito (Friendly Name): " + certificadoRecebido.getName() + "\n";
				texto += "E-mail: " + certificadoRecebido.getEmail() + "\n";

				switch (JOptionPane.showConfirmDialog(null, texto)) {
				case 0:
					if(editFlag) {
						if(query.editUser(user, AuthenticationUser.getLogin())) {
							query.RegisterLog(AuthenticationUser.getLogin(), null , 7004);
							JOptionPane.showMessageDialog(this, "Editar realizado com sucesso!", // mensagem
									"Sucesso!", // titulo da janela
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							query.RegisterLog(AuthenticationUser.getLogin(), null , 7005);
							JOptionPane.showMessageDialog(this, "Editar n�o realizado!", // mensagem
									"Erro!", // titulo da janela
									JOptionPane.INFORMATION_MESSAGE);
						}
						
					}else {
						if(query.registerUser(user, login)) {
							query.RegisterLog(AuthenticationUser.getLogin(), null , 6005);
							JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!", // mensagem
									"Sucesso!", // titulo da janela
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							query.RegisterLog(AuthenticationUser.getLogin(), null , 6006);
							JOptionPane.showMessageDialog(this, "Cadastro n�o realizado!", // mensagem
									"Erro!", // titulo da janela
									JOptionPane.INFORMATION_MESSAGE);
						}
						
					}
					
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
			}
		} else if (ae.getSource() == btnCancelar) {
			
			this.dispose();
			new ForthScreen(query, login, gpo, nome, qtdUsuariosDoSistema, totalAcessosDoUsuario, totalConsultaDoUsuario);

		} else if (ae.getSource() == btnAlterar) {
			query.RegisterLog(AuthenticationUser.getLogin(), null , 5003);
			editFlag = true;
			flag_cadastrar_ou_alterar = false;
			this.panel.remove(btnCadastrar);
			this.panel.remove(btnAlterar);
			this.panel.remove(btnConsultar);
			this.panel.remove(btnSair);
			this.panel.remove(lblQtd);

			this.panel.repaint();

			lblQtd = new JLabel("Total de acessos do usu�rio: " + totalAcessosDoUsuario);
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
			query.RegisterLog(AuthenticationUser.getLogin(), null , 5004);
			query.RegisterLog(AuthenticationUser.getLogin(), null , 8001);
			this.panel.remove(btnCadastrar);
			this.panel.remove(btnAlterar);
			this.panel.remove(btnConsultar);
			this.panel.remove(btnSair);
			this.panel.remove(lblQtd);
			this.panel.remove(lblQtd);

			this.panel.repaint();

			lblQtd = new JLabel("Total de consultas do usu�rio: " + totalConsultaDoUsuario);
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
			
			btnAcessarTodosOsArquivos = new JButton("Acessar arquivos");
			btnAcessarTodosOsArquivos.setBounds(15, 250, 78, 25);
			btnAcessarTodosOsArquivos.addActionListener(this);
			btnAcessarTodosOsArquivos.setEnabled(fileListExist);
			this.panel.add(btnAcessarTodosOsArquivos);
			
			btnCancelar = new JButton("Cancelar");
			btnCancelar.setBounds(200, 430, 90, 25);
			btnCancelar.addActionListener(this);
			this.panel.add(btnCancelar);

		} else if (ae.getSource() == btnOk) {
			query.RegisterLog(AuthenticationUser.getLogin(), null , 9003);
			System.exit(0);

		} else if (ae.getSource() == btnSair) {
			query.RegisterLog(AuthenticationUser.getLogin(), null , 5005);
			query.RegisterLog(AuthenticationUser.getLogin(), null , 9001);
			this.panel.remove(btnCadastrar);
			this.panel.remove(btnAlterar);
			this.panel.remove(btnConsultar);
			this.panel.remove(btnSair);
			this.panel.remove(lblQtd);

			this.panel.repaint();

			JLabel lbl = new JLabel("Sa�da do Sistema");
			lbl.setFont(fonteCabecalho);
			lbl.setBounds(15, -150, 500, 500);
			this.panel.add(lbl);

			JLabel lbl2 = new JLabel("Voc� est� prestes a sair do sistema, deseja sair?");
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
