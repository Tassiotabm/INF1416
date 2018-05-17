package backend;

import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Random;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;

public class KeyAuthentication {

	public KeyAuthentication() {
		// TODO Auto-generated constructor stub
	}

	public byte[] decryptPKCS5(byte[] encoded, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encoded);
    }

	public PrivateKey decryptPrivateKey ( String secret, String pkLocation ) {

		// Generating secret key
		SecretKeyGenerator skg = new SecretKeyGenerator(secret);
		SecretKey secretKey = skg.generateSecretKey("SHA1PRNG", "DES", 56);
		//System.out.println("Secret key in hexa:\n" + DatatypeConverter.printHexBinary(secretKey.getEncoded()) + "\n");
	
		// Decrypting private key
		return decryptPrivateKeyFile(pkLocation, secretKey);
	}
	
    public PrivateKey decryptPrivateKeyFile(String filePath, SecretKey secretKey) {
    		
    	// Getting private key file data as byte array
		Path path = Paths.get(filePath);
		byte[] fileData = null;
		try {
			fileData = Files.readAllBytes(path);
		} catch ( IOException e ) {
			return null;
		}
		
		// Decrypting data to PEM format
		Cipher cipher = null;
		byte[] pemData = null;
		try {
			cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			pemData = cipher.doFinal(fileData);
		} catch ( NoSuchAlgorithmException e ) {
			System.err.println("[ERROR-PRIVATE KEY DECRYPT] Non-existing algorithm for Cipher: DES/ECB/PKCS5Padding");
			System.exit(1);
		} catch ( NoSuchPaddingException e ) {
			System.err.println("[ERROR-PRIVATE KEY DECRYPT] Non-existing padding for Cipher: DES/ECB/PKCS5Padding");
			System.exit(1);
		} catch ( InvalidKeyException e ) {
			System.err.println("[ERROR-PRIVATE KEY DECRYPT] Invalid secret key");
			return null;
			//System.exit(1);
		} catch ( IllegalBlockSizeException e ) {
			System.err.println("[ERROR-PRIVATE KEY DECRYPT] Actual encrypted private key data byte size (" + Integer.toString(fileData.length) + ") doesn't match with expected by Cipher");
			System.exit(1);
		} catch ( BadPaddingException e ) {
			System.err.println("[ERROR-PRIVATE KEY DECRYPT] Bad padding in private key file data expected by Cipher");
			return null;
			//System.exit(1);
		}

		String privKeyPEM = null;
		try {
			privKeyPEM = new String(pemData, "UTF-8");
		} catch ( UnsupportedEncodingException e ) {
			System.err.println("[ERROR-PRIVATE KEY DECRYPT] Non-supported character encoding for PEM data: UTF-8");
			System.exit(1);
		}
		//System.out.println("PEM format:\n" + privKeyPEM);

        // Decoding Base64
        privKeyPEM = privKeyPEM.replace("-----BEGIN PRIVATE KEY-----\n", "").replace("-----END PRIVATE KEY-----", "").replaceAll("\\s","");
		//System.out.println("Base64 code:\n" + privKeyPEM + "\n");
		byte[] pkcs8Data = Base64.getDecoder().decode(privKeyPEM);
		//System.out.println("PKCS8 encrypted key:\n" + new String(pkcs8Data, "UTF-8") + "\n");

		// Generating private key
    	PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8Data);
    	KeyFactory keyFactory = null;
    	PrivateKey privateKey = null;
    	try {
      		keyFactory = KeyFactory.getInstance("RSA");
      		privateKey = keyFactory.generatePrivate(keySpec);
      	} catch ( NoSuchAlgorithmException e ) {
      		System.err.println("[ERROR-PRIVATE KEY DECRYPT] Non-existing algorithm for KeyFactory: RSA");
			System.exit(1);
      	} catch ( InvalidKeySpecException e ) {
      		System.err.println("[ERROR-PRIVATE KEY DECRYPT] Invalid encoding to generate private key");
			System.exit(1);
      	}
		return privateKey;
    
    }

    public PublicKey retrivePublicKey(byte[] contentFile) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec encodeX509 = new X509EncodedKeySpec(contentFile);
        PublicKey publicKey = keyFactory.generatePublic(encodeX509);

        return publicKey;
    }

    public boolean isSign(PrivateKey privateKey, PublicKey publicKey) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, SignatureException {

    	//Gerar assinatura Digital
        byte[] bytes = new byte[1024];
        Random random = new Random();
        random.nextBytes(bytes);
        Signature sign = Signature.getInstance("MD5WithRSA");
        
        sign.initSign(privateKey);
        sign.update(bytes);

        byte[] signPrivateKey = sign.sign();
        sign.initVerify(publicKey);

        sign.update(bytes);

        if (sign.verify(signPrivateKey)) {
            return true;
        }
        return false;
    }
}
