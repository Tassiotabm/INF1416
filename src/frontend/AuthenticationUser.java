package frontend;

public abstract class AuthenticationUser {

	private static String Login;
	private static String Group;
	
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

}
