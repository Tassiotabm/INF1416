package frontend;

public abstract class AuthenticationUser {

	private static String Email;
	private static String Hash;
	
	public  AuthenticationUser() {
		// TODO Auto-generated constructor stub
	}

	public static String getEmail() {
		return Email;
	}

	public static void setEmail(String email) {
		Email = email;
	}

	public static String getHash() {
		return Hash;
	}

	public static void setHash(String hash) {
		Hash = hash;
	}

}
