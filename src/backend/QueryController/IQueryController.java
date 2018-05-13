package backend.QueryController;

import backend.Usuario;

public interface IQueryController {

	public boolean findUser(String email);
	public boolean checkPassword(String [][] matrix);
	public boolean registerUser(Usuario user);
	public boolean editUser(Usuario user);

}
