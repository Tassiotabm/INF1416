package backend.QueryController;

import Model.Usuario;

public interface IQueryController {

	public boolean findUser(String email);
	public boolean validatePassword(String Login,String [][] password);
	public boolean registerUser(Usuario user, String login);
	public boolean editUser(Usuario user,String Login);
	public boolean checkCertificate(String certificatePath, String secretKey, String login);
	public int GetUsersCount();
	public void RegisterLog(String login,String filePath, int messageId);
}
