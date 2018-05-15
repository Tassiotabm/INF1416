package backend;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateException;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.io.FileNotFoundException;
//import javax.xml.bind.DatatypeConverter;

public final class CertificateController {

	X509Certificate certificate = null;
	
	public CertificateController (String path) {
		readCertificate(path);
	}
	
	public PublicKey getPublicKey() {
		return certificate.getPublicKey();
	}
	
	public String getLogin() {
		String info = certificate.getSubjectX500Principal().toString();
		return info.substring(info.indexOf("=")+1, info.indexOf(","));
	}

	public BigInteger getSerial() {
		return certificate.getSerialNumber();
	}
	
	public String getType() {
		return certificate.getType();
	}
	
	public int getVersion() {
		return certificate.getVersion();
	}
	
	public String getEmissor(){
		String info = certificate.getIssuerX500Principal().getName();
		String [] splitedInfo = info.split("\\,(.*?)\\=");
		return splitedInfo[2];
	}
	
	public String getDate() {
		return certificate.getNotAfter().toString();
	}
	
	public String getEmail() {
		String info = certificate.getIssuerX500Principal().toString();
		return info.substring(info.indexOf("=")+1, info.indexOf(","));
	}
	
	public String getName() {
		String info = certificate.getIssuerX500Principal().getName();
		String [] splitedInfo = info.split("\\,(.*?)\\=");
		return splitedInfo[1];
	}
	
	private void readCertificate ( String path ) {
		
		FileInputStream fis = null;
		CertificateFactory cf = null;
		try {
        	fis = new FileInputStream(path);
        	cf = CertificateFactory.getInstance("X.509");
        	certificate = (X509Certificate)cf.generateCertificate(fis);
        	fis.close();
        } catch ( FileNotFoundException e ) {
			System.exit(1);
        } catch ( CertificateException e ) {
        	System.err.println("[ERROR-PUBLIC KEY RETRIEVE] Wrong certificate pattern: X.509");
        	System.out.println(e.toString());
			System.exit(1);
        } catch ( IOException e ) {
			System.exit(1);
        }
	}
}