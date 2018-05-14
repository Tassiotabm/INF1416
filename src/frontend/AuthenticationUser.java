package frontend;

public abstract class AuthenticationUser {

	private static String Login;
	
	public  AuthenticationUser() {
		// TODO Auto-generated constructor stub
	}

	public static String getLogin() {
		return Login;
	}

	public static void setLogin(String login) {
		Login = login;
	}

}
