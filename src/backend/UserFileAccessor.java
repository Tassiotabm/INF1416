package backend;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.PrivateKey;


public class UserFileAccessor {

	private String secretName;
	private String fileName;

	private void createFile ( byte[] fileContent, String fileContentLocation, String _secretName ) {

		//String path = fileContentLocation.substring(0, fileContentLocation.lastIndexOf("/")).concat("/" + _secretName);
		
		String path = fileContentLocation.substring(0, fileContentLocation.lastIndexOf("\\"));
		String path2 = path.concat("\\" + _secretName + ".enc");
		File f = new File(path2);
		f.getParentFile().mkdirs();
		FileOutputStream fos = null;
		try {
			f.createNewFile();
			fos = new FileOutputStream(path2);
			fos.write(fileContent);
			fos.close();
		} catch ( IOException e ) {
			System.err.println("[ERROR-USER FILE ACCESS] Problem with file in path: " + path + "\nERROR MESSAGE: " + e.getCause().getMessage());
			System.exit(1);
		}
	}
	
	public boolean checkFileAccess ( String fileRow, String user, String userGroup ) {

		String ownerFile = fileRow.substring(fileRow.indexOf(" "), fileRow.lastIndexOf(" ")).trim();
		ownerFile = ownerFile.substring(ownerFile.indexOf(" ")).trim();
		String fileGroup = fileRow.substring(fileRow.lastIndexOf(" ")).replace("\n", "").trim();
		
		if(ownerFile.equals(user) || fileGroup.equals(userGroup)) {
			this.secretName = fileRow.substring(fileRow.indexOf(" ")).trim();
			this.secretName = secretName.substring(0, secretName.indexOf(" ")).trim();
			this.fileName = fileRow.substring(0, fileRow.indexOf(" ")).trim();
			return true;
		}
		System.err.println("User has no permition to access file");

		return false;
	}

	public boolean accessFile ( PrivateKey privateKey, CertificateController cr, String folderLocation, String fileSelected) {

		FileContentRetriever fcr = new FileContentRetriever(privateKey, cr.getPublicKey());
		String onlyFileName = fileSelected.substring(0,fileSelected.indexOf(" "));
		String fileEnc = folderLocation.substring(0, folderLocation.lastIndexOf("\\")).concat("\\" + onlyFileName + ".enc");
		String fileEnv = fileEnc.replace(".enc", ".env");
		String fileAsd = fileEnc.replace(".enc", ".asd");
		
		// Checking file's integrity and authenticity
		if(fcr.hasIntegrityAndAuthenticity(fileEnc, fileEnv, fileAsd)) {

			byte[] fileContent = fcr.retrieveFileContent();
			if(fileContent == null)// TODO
				return false;

			// Creating new file
			String[] secretName = fileSelected.split("\\s+");
			createFile(fileContent, folderLocation, "new_"+secretName[0]);
		}
		else {
			System.out.println("[ERROR-USER FILE ACCESS] File's integrity and authenticity compromised");
			//System.exit(1);
			return false;
		}
		return true;
	}


}
