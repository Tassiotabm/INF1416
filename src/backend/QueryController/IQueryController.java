package backend.QueryController;

import Model.Usuario;

public interface IQueryController {

	public boolean findUser(String email);
	public boolean validatePassword(String Login,String [][] password);
	public boolean registerUser(Usuario user);
	public boolean editUser(Usuario user);
	public boolean checkCertificate(String certificatePath, String secretKey, String login);
}
