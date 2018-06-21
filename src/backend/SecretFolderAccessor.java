package backend;

import java.security.PrivateKey;
import java.security.PublicKey;

public class SecretFolderAccessor {

	private FileContentRetriever fileContentRetriever = null;


	public boolean checkFolderAccess ( CertificateController cr, String folderContentFileLocation,
			String virtualEnvelopeLocation, String virtualSignatureLocation, PrivateKey privateKey ) {

		PublicKey publicKey = cr.getPublicKey();

		FileContentRetriever fcr = new FileContentRetriever(privateKey, publicKey);
		if(fcr.hasIntegrityAndAuthenticity(folderContentFileLocation, virtualEnvelopeLocation, virtualSignatureLocation)) {
			fileContentRetriever = fcr;
			return true;
		}
		System.err.println("Folder's integrity and authenticity compromised");
		
		return false;
	}

	public byte[] getFolderContent () {
		return fileContentRetriever.retrieveFileContent();
	}

}

