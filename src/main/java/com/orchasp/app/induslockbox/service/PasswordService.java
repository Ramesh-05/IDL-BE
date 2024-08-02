package com.orchasp.app.induslockbox.service;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service
public class PasswordService {
	
	private static final String ALGORITHM = "AES";
    private static final String KEY = "MySuperSecretKey"; 
    
	public String encrypt(String value) {
        try {
            SecretKeySpec key = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedValue = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encryptedValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String encryptedValue) {
        try {
            SecretKeySpec key = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedValue = Base64.getDecoder().decode(encryptedValue);
            byte[] decryptedValue = cipher.doFinal(decodedValue);
            return new String(decryptedValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
