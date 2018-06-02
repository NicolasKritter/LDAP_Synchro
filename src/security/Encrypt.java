package security;


import java.nio.charset.Charset;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt {

	private static final String  KEY = "mxeu1jjwwj6iutoj";
	private static final SecretKeySpec   AES_KEY = new SecretKeySpec(KEY.getBytes(), "AES");
	public static byte[] getBytesFromString(String str) {
		 byte[] res = new byte[str.length()];
	        for (int i=0; i<str.length(); i++) {
	        	res[i] = (byte) str.charAt(i);
	        }
	        return res;
	}
	
	private static String getStringfromBytes(byte[] bytes) {
		 StringBuilder sb = new StringBuilder();
         for (byte b: bytes) {
             sb.append((char)b);
         }

         // the encrypted String
        return sb.toString();
	}
	
	
	public static String encryptString(String toEncrypt) {
		
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, AES_KEY);
			byte[] encrypted = cipher.doFinal(toEncrypt.getBytes());

			return getStringfromBytes(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String decryptString(String toDecrypt) {
        byte[] toDecryptBytes =getBytesFromString(toDecrypt);
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, AES_KEY);
			String decrypted = new String(cipher.doFinal(toDecryptBytes));
			return decrypted;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public static String generateString(int n) {
		char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < n; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		return sb.toString();
	}
	

}
