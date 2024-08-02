package com.orchasp.app.induslockbox.service;
//Source code is decompiled from a .class file using FernFlower decompiler.

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class CryptoUtils {
private static final String TRANSFORMATION = "AES";
private static final int keySize = 128;
private static final int iterationCount = 1000;

public CryptoUtils() {
}

public static void encrypt(String algorithm, String passphrase, String salt, File inputFile, File outputFile) throws Exception {
   doCrypto(algorithm, 1, passphrase, salt, inputFile, outputFile);
}

public static String decrypt(String algorithm, String passphrase, String salt, File inputFile, File outputFile) throws Exception {
   return doCryptoDecrypt(algorithm, 2, passphrase, salt, inputFile, outputFile);
}

private static void doCrypto(String algorithm, int cipherMode, String passphrase, String salt, File inputFile, File outputFile) throws Exception {
   try {
      SecretKey secretKey = generateKey(passphrase, salt, algorithm);
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(cipherMode, secretKey);
      FileInputStream inputStream = new FileInputStream(inputFile);
      byte[] inputBytes = new byte[(int)inputFile.length()];
      inputStream.read(inputBytes);
      byte[] outputBytes = cipher.doFinal(inputBytes);
      FileOutputStream outputStream = new FileOutputStream(outputFile);
      outputStream.write(outputBytes);
      inputStream.close();
      outputStream.close();
   } catch (NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException | NoSuchPaddingException var12) {
      throw new Exception("Error encrypting/decrypting file", var12);
   }
}

private static String doCryptoDecrypt(String algorithm, int cipherMode, String passphrase, String salt, File inputFile, File outputFile) throws Exception {
   try {
      String base64Image = "";
      SecretKey secretKey = generateKey(passphrase, salt, algorithm);
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(cipherMode, secretKey);
      FileInputStream inputStream = new FileInputStream(inputFile);
      byte[] inputBytes = new byte[(int)inputFile.length()];
      inputStream.read(inputBytes);
      byte[] outputBytes = cipher.doFinal(inputBytes);
      base64Image = Base64.getEncoder().encodeToString(outputBytes);
      inputStream.close();
      return base64Image;
   } catch (NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException | NoSuchPaddingException var12) {
      throw new Exception("Error encrypting/decrypting file", var12);
   }
}

public static SecretKey generateKey(String passphrase, String salt, String algorithm) {
   try {
      SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
      KeySpec spec = new PBEKeySpec(passphrase.toCharArray(), salt.getBytes(), 1000, 128);
      SecretKey key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
      return key;
   } catch (InvalidKeySpecException | NoSuchAlgorithmException var6) {
      throw fail(var6);
   }
}

private static IllegalStateException fail(Exception e) {
   return new IllegalStateException(e);
}

public static byte[] hex(String str) {
   try {
      return Hex.decodeHex(str.toCharArray());
   } catch (DecoderException var2) {
      throw new IllegalStateException(var2);
   }
}
}
