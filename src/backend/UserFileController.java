package backend;

import java.security.PrivateKey;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;

public class UserFileController {

	private String secretName = null;
	private String fileName = null;

	public boolean checkFileAccess ( String fileRow, String user, String userGroup ) {

		String fileOwner = fileRow.substring(fileRow.indexOf(" "), fileRow.lastIndexOf(" ")).trim();
		fileOwner = fileOwner.substring(fileOwner.indexOf(" ")).trim();
		String fileGroup = fileRow.substring(fileRow.lastIndexOf(" ")).replace("\n", "").trim();
		
		if(fileOwner.equals(user) || fileGroup.equals(userGroup)) {
			secretName = fileRow.substring(fileRow.indexOf(" ")).trim();
			secretName = secretName.substring(0, secretName.indexOf(" ")).trim();
			fileName = fileRow.substring(0, fileRow.indexOf(" ")).trim();
			return true;
		}
		System.err.println("[ERROR-USER FILE ACCESS] User has no permition to access file");

		return false;
	}

	public boolean accessFile ( PrivateKey privateKey, CertificateController cr, String folderLocation ) {

		FileContentRetriever fcr = new FileContentRetriever(privateKey, cr.getPublicKey());

		String fileEnc = folderLocation.substring(0, folderLocation.lastIndexOf("/")).concat("/" + fileName + ".enc");
		String fileEnv = fileEnc.replace(".enc", ".env");
		String fileAsd = fileEnc.replace(".enc", ".asd");
		
		// Checking file's integrity and authenticity
		if(fcr.hasIntegrityAndAuthenticity(fileEnc, fileEnv, fileAsd)) {

			byte[] fileContent = fcr.retrieveFileContent();
			if(fileContent == null)// TODO
				return false;

			// Creating new file
			createFile(fileContent, fileEnc);
		}
		else {
			System.out.println("[ERROR-USER FILE ACCESS] File's integrity and authenticity compromised");
			//System.exit(1);
			return false;
		}
		return true;
	}

	private void createFile ( byte[] fileContent, String fileContentLocation ) {

		String path = fileContentLocation.substring(0, fileContentLocation.lastIndexOf("/")).concat("/" + secretName);
		File f = new File(path);
		f.getParentFile().mkdirs();
		FileOutputStream fos = null;
		try {
			f.createNewFile();
			fos = new FileOutputStream(path);
			fos.write(fileContent);
			fos.close();
		} catch ( IOException e ) {
			System.err.println("[ERROR-USER FILE ACCESS] Problem with file in path: " + path + "\nERROR MESSAGE: " + e.getCause().getMessage());
			System.exit(1);
		}
	}
}