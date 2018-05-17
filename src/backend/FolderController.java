package backend;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

public class FolderController {

	private PrivateKey userPK = null;
		
	private CertificateController userCertificate = null;
	
	private int totalRegisteredUsers = 0;
	
	private CertificateController registryCertificate = null;
	private SecretFolderAccessor sfa = null;
	public FolderController() {
		// TODO Auto-generated constructor stub
		
	}
	
	public boolean checkSecretFolder(String secretFolderPath) {
		// TODO Auto-generated method stub
		sfa = new SecretFolderAccessor();
		
		String folderEnv = secretFolderPath.replace(".enc", ".env");
		String folderAsd = secretFolderPath.replace(".enc", ".asd");
		
		return sfa.checkFolderAccess(userCertificate, secretFolderPath, folderEnv, folderAsd, userPK);
	}
	
	public byte[] getSecretFolderContent() {
		// TODO Auto-generated method stub
		return sfa.getFolderContent();
	}
}
