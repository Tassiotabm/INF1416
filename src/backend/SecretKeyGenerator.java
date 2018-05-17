package backend;


import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

public class SecretKeyGenerator {

	private String secret = null;
	private SecureRandom secureRandom = null;

	public SecretKeyGenerator ( String secret ) {
		this.secret = secret;
	}

	public SecretKey generateSecretKey ( String secureRandomAlgorithm, String keyGeneratorAlgorithm, int keySize ) {
		generateSeed(secureRandomAlgorithm);
		KeyGenerator keyGenerator = null;
		try {
			keyGenerator = KeyGenerator.getInstance(keyGeneratorAlgorithm); // DES
		} catch ( NoSuchAlgorithmException e ) {
			System.err.println("[ERROR-SECRET KEY] Non-existing algorithm for KeyGeneratir: " + keyGeneratorAlgorithm);
			System.exit(1);
		}
		keyGenerator.init(keySize, secureRandom); // key size is 56 bits
		return keyGenerator.generateKey();
	}

	private void generateSeed ( String algorithm ) {
		String charEncoding = "us-ascii";
		try {
			secureRandom = SecureRandom.getInstance(algorithm); //SHA1PRNG
			secureRandom.setSeed(secret.getBytes(charEncoding));
		} catch ( NoSuchAlgorithmException e ) {
			System.err.println("[ERROR-SECRET KEY] Non-existing algorithm for SecureRandom: " + algorithm);
			System.exit(1);
		} catch ( UnsupportedEncodingException e ) {
			System.err.println("[ERROR-SECRET KEY] Non-supported character encoding: " + charEncoding);
			System.exit(1);
		}
	}
}