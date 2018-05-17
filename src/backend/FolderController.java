package backend;

import java.security.PrivateKey;

import frontend.AuthenticationUser;


public class FolderController {

	private PrivateKey userPrivateKey = null;
		
	private CertificateController userCertificate = null;	
	private CertificateController registryCertificate = null;
	private SecretFolderAccessor folderAcessor = null;
	private UserFileAccessor ufa = null;
	
	public FolderController(CertificateController _userCertificate, PrivateKey _userPrivateKey) {
		this.userPrivateKey = _userPrivateKey;
		this.userCertificate = _userCertificate;
	}
	
	public boolean checkSecretFolder(String secretFolderPath) {
		// TODO Auto-generated method stub
		this.folderAcessor = new SecretFolderAccessor();
		
		String folderEnv = secretFolderPath.replace(".enc", ".env");
		String folderAsd = secretFolderPath.replace(".enc", ".asd");
		
		return folderAcessor.checkFolderAccess(userCertificate, secretFolderPath, folderEnv, folderAsd, userPrivateKey);
	}
	
	public boolean checkSecretFileAcess(String filePath) {
		ufa = new UserFileAccessor();
		return ufa.checkFileAccess(filePath, AuthenticationUser.getLogin(), AuthenticationUser.getGroup());
	}
	
	public byte[] getSecretFolderContent() {
		// TODO Auto-generated method stub
		return folderAcessor.getFolderContent();
	}
	
	public boolean acessSecretFile(String filePath) {
		return ufa.accessFile(AuthenticationUser.getPrivateKey(), AuthenticationUser.getCertificateController(), filePath);
	}
}
