package backend;

import java.security.PrivateKey;
import java.security.PublicKey;

public class SecretFolderAccessor {

	private FileContentRetriever fileContentRetriever = null;

	// User must give folder he wants to see and private key location. Certificate, env and asd comes from calling class
	public boolean checkFolderAccess ( CertificateController cr, String folderContentFileLocation,
			String virtualEnvelopeLocation, String virtualSignatureLocation, PrivateKey privateKey ) {

		// Getting public key
		PublicKey publicKey = cr.getPublicKey();

		// Checking selected user folder's integrity and authenticity
		FileContentRetriever fcr = new FileContentRetriever(privateKey, publicKey);
		if(fcr.hasIntegrityAndAuthenticity(folderContentFileLocation, virtualEnvelopeLocation, virtualSignatureLocation)) {
			fileContentRetriever = fcr;
			return true;
		}
		//System.err.println("[ERROR-FOLDER ACCESS] Folder's integrity and authenticity compromised");
		
		return false;
	}

	public byte[] getFolderContent () {
		return fileContentRetriever.retrieveFileContent();
	}

}

