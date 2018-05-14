package backend;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

public class KeyAuthentication {

	public KeyAuthentication() {
		// TODO Auto-generated constructor stub
	}

	public byte[] decryptPKCS5(byte[] encoded, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encoded);
    }

    public PrivateKey decryptPrivateKey(byte[] encodedPrivateKey, String secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(secretKey.getBytes());
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(56, random);
        byte[] pkcs8PrivateKey = decryptPKCS5(encodedPrivateKey, keyGen.generateKey());

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(pkcs8PrivateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
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
