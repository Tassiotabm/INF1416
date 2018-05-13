package backend;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;

/*Valor_Armazenado = HEX(HASH_SHA1(senha_texto_plano + SALT))
HEX = representação hexadecimal.
HASH_SHA1 = função hash SHA-1.
+ = concatenação de string.
senha_texto_plano = senha em texto plano (string).
SALT = valor aleatório composto de 10 caracteres do conjunto [A-Z][a-z][0-9] (string)*/

public abstract class PasswordAuthentication
{
    private static final String salt = "TassioEMarcio2018";

    public static String getSaltedHash(String password) throws Exception {
    	
    	String pass = DigestUtils.sha1Hex(password+salt);
    	return null;
    }

    /** Checks whether given plaintext password corresponds 
        to a stored salted hash of the password. */
    public static boolean check(String password, String stored) throws Exception{
    	return true;
    }
    
}