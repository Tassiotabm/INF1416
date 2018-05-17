package frontend;

import java.security.PrivateKey;

import backend.CertificateController;

public abstract class AuthenticationUser {

	private static String Login;
	private static String Group;
	private static PrivateKey privateKey;
	private static CertificateController certificateController;
	
	public  AuthenticationUser() {
		// TODO Auto-generated constructor stub
	}

	public static String getLogin() {
		return Login;
	}

	public static void setLogin(String login) {
		Login = login;
	}

	public static String getGroup() {
		return Group;
	}

	public static void setGroup(String group) {
		Group = group;
	}

	public static CertificateController getCertificateController() {
		return certificateController;
	}

	public static void setCertificateController(CertificateController certificateController) {
		AuthenticationUser.certificateController = certificateController;
	}

	public static PrivateKey getPrivateKey() {
		return privateKey;
	}

	public static void setPrivateKey(PrivateKey privateKey) {
		AuthenticationUser.privateKey = privateKey;
	}

}
