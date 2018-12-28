package com.revature.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingUtil {
		//need to convert byte to hex string
	private static String bytesToHex(byte[] hash) {
	    StringBuffer hexString = new StringBuffer();
	    for (int i = 0; i < hash.length; i++) {
	    String hex = Integer.toHexString(0xff & hash[i]);
	    if(hex.length() == 1) hexString.append('0');
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	
	//this is where the password gets hashed by saving to database
	//substring because the hash is too long
	//42 because its the answer to all things
	public static String hashword(String p) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		String salt = System.getProperty("salt");
		String plaintext = p+salt;
		byte[] finalHash = digest.digest(
				  plaintext.getBytes(StandardCharsets.UTF_8));
		System.out.println("Hash: " + bytesToHex(finalHash).substring(0, 42));
		return bytesToHex(finalHash).substring(0, 42);
	}
}
