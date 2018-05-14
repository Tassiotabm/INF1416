package Model;

public class Usuario {
	private String grupo;
	private String caminhoCertificado;
	private String senha;
	private int totalAcessosDoUsuario;
	
	public Usuario(String grupo, String caminhoCertificado,String senha) {
		super();
		this.grupo = grupo;
		this.caminhoCertificado=caminhoCertificado;
		this.senha=senha;
	}
	
	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public int getTotalAcessosDoUsuario() {
		return totalAcessosDoUsuario;
	}

	public void setTotalAcessosDoUsuario(int totalAcessosDoUsuario) {
		this.totalAcessosDoUsuario = totalAcessosDoUsuario;
	}

	public String getCaminhoCertificado() {
		return caminhoCertificado;
	}

	public void setCaminhoCertificado(String caminhoCertificado) {
		this.caminhoCertificado = caminhoCertificado;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
