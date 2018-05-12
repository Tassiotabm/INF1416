package backend.QueryController;

public interface IQueryController {

	public boolean findUser(String email);
	public boolean checkPassword(String [][] matrix);
	
}
