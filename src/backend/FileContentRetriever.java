package backend;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Key;
import java.security.Signature;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;


public class FileContentRetriever {

	private PrivateKey privateKey = null;
	private PublicKey publicKey = null;
	private byte[] encData = null;

	public FileContentRetriever ( PrivateKey privKey, PublicKey publKey ) {
		privateKey = privKey;
		publicKey = publKey;
	}

	public boolean hasIntegrityAndAuthenticity ( String encLocation, String envLocation, String asdLocation ) {

		// Making secret key from seed extracted from env file
		SecretKey secretKey = getSecretKey(envLocation);
		if(secretKey == null)
			return false; // TODO

		// Getting digital signature in asd file
		byte[] signatureData = getFileContentInByteArray(asdLocation);

		// Decrypting enc file
		decryptEncFile(encLocation, secretKey);

		// Verifying enc's integrity
		Signature sig = null;
		boolean result = false;
		try {
			sig = Signature.getInstance("MD5withRSA");
			sig.initVerify(publicKey);
			sig.update(encData);
			result = sig.verify(signatureData);

			// For privacy measures...
			if(!result)
				encData = null;
		} catch ( NoSuchAlgorithmException e) {
			System.err.println("Non-existing algorithm for Signature: MD5withRSA");
			return false; // TODO
		} catch ( InvalidKeyException e ) {
			System.err.println("Invalid key used in Signature");
			return false;
		} catch ( SignatureException e ) {
			System.err.println("Problem with update data expected OR verifying signature in Signature\nFile: " + encLocation);
			return false;
		}

		return result;
	}

	public byte[] retrieveFileContent() {
		return encData;
	}

	private byte[] getFileContentInByteArray ( String filePath ) {
		Path path = Paths.get(filePath);
		byte[] content = null;
		try {
			content = Files.readAllBytes(path);
		} catch ( IOException e ) {
			System.err.println("Problem to read file in path: " + filePath);
			System.exit(1);
		}
		return content;
	}

	private byte[] decryptData ( String algorithm, Key key, byte[] data ) {
		Cipher cipher = null;
		byte[] decryptedData = null;
		try {
			cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.DECRYPT_MODE, key);
			decryptedData = cipher.doFinal(data);
		} catch ( NoSuchAlgorithmException e ) {
			System.err.println("Non-existing algorithm for Cipher: " + algorithm);
			return null;// TODO
		} catch ( NoSuchPaddingException e ) {
			System.err.println("Non-existing padding for Cipher: " + algorithm);
			return null;
		} catch ( InvalidKeyException e ) {
			System.err.println("Invalid key used in Cipher: " + DatatypeConverter.printHexBinary(key.getEncoded()));
			return null;
		} catch ( IllegalBlockSizeException e ) {
			System.err.println("Actual encrypted data byte size (" + Integer.toString(data.length) + ") doesn't match with expected by Cipher");
			return null;
		} catch ( BadPaddingException e ) {
			System.err.println("Bad padding in data expected by Cipher");
			e.printStackTrace();
			return null;
		}
        
        return decryptedData;
	}

	private SecretKey getSecretKey ( String envFileLocation ) {

		// Getting env file data as byte array
		byte[] fileData = getFileContentInByteArray(envFileLocation);
		
		// Decrypting data
        byte[] secretData = decryptData("RSA", (Key)privateKey, fileData);
        if(secretData == null)// TODO
        	return null;

        // Generating secret key
        SecretKeyGenerator skg = null;
        try {
        	skg = new SecretKeyGenerator(new String(secretData, "UTF-8"));
        } catch ( UnsupportedEncodingException e ) {
        	System.err.println("[ERROR-FILE CONTENT RETRIEVE] Non-supported character encoding for secret data: UTF-8");
			System.exit(1);
        }
        //System.out.println("\nSecret key generated from env file's seed, in hexa:\n" + DatatypeConverter.printHexBinary(skg.generateSecretKey("SHA1PRNG", "DES", 56).getEncoded()));
        return skg.generateSecretKey("SHA1PRNG", "DES", 56);
	}

	private void decryptEncFile ( String encFileLocation, SecretKey secretKey ) {

		// Getting enc file data as byte array
		byte[] fileData = getFileContentInByteArray(encFileLocation);

		// Decrypting enc content
        encData = decryptData("DES/ECB/PKCS5Padding", (Key)secretKey, fileData);
	}
}