package EncryptionApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.*; //Security policies, etc.

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Path;

public class Ciphers 
{	
	
	public static void encrypt(String password, Path path, String fileName) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IOException, IllegalBlockSizeException, BadPaddingException
	{
		//Initialise the SHA-256 algorithm and create 32-byte array.
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(password.getBytes("UTF-8"));
		
		//Split the hash into two 16-byte keys
		byte[] AESkey = Arrays.copyOf(hash, 16);
		byte[] MACkey = Arrays.copyOfRange(hash, 16, 32);
		
		//Text file split into an array of bytes
		String stringPath = path.toString();
		Path newPath = Paths.get(stringPath);
		byte[] input = Files.readAllBytes(newPath);
		
		//Initialise the AESkey bytes
		SecretKeySpec keySpec = new SecretKeySpec(AESkey, "AES");
		
		//Create the random Initial Vector
		byte[] iv = new byte[16];
		SecureRandom randomBytes = new SecureRandom();
		randomBytes.nextBytes(iv);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
		
		//Encrypt the file
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
		byte[] encrypted = cipher.doFinal(input);
		
		//Create HMAC, update to include IV and ciphertext
		Mac mac = Mac.getInstance("HmacSHA1");
		SecretKeySpec macKey = new SecretKeySpec(MACkey, "HmacSHA1");
		mac.init(macKey);
		mac.update(iv);
		mac.update(encrypted);
		byte[] authKey = mac.doFinal();
		
		//Write encrypted data to new file appended with .enc and remove old one
		File encFile = new File(path.toString() + ".enc");
		FileOutputStream fos = new FileOutputStream(encFile);
		fos.write(iv);
		fos.write(encrypted);
		fos.write(authKey);
		
		File file = newPath.toFile();
		file.delete();
		fos.close();
		
	}
	
	public static void decrypt(String password, Path path, String fileName) throws NoSuchAlgorithmException, IOException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
	{
		//Convert entire file into a byte array
		String stringPath = path.toString();
		Path newPath = Paths.get(stringPath);
		byte[] input = Files.readAllBytes(newPath); 
		
		//Extract ciphertext
		byte[] onlyText = Arrays.copyOfRange(input, 16, input.length-20);
		
		//Hash the password
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(password.getBytes("UTF-8"));
		
		//Split the hash into two 16-byte keys
		byte[] AESkey = Arrays.copyOf(hash, 16);
		byte[] MACkey = Arrays.copyOfRange(hash, 16, 32);
		
        //Extract the Initial Vector
		byte[] iv = new byte[16];
		System.arraycopy(input, 0, iv, 0, iv.length);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
		
		//Initialise the AESkey bytes
		SecretKeySpec keySpec = new SecretKeySpec(AESkey, "AES");
		
		//Extract HMAC
		byte[] encAuthKey = new byte[20];
		System.arraycopy(input, (input.length-20), encAuthKey, 0, 20);
		
		//Create HMAC from input password, ciphertext and IV
		Mac mac = Mac.getInstance("HmacSHA1");
		SecretKeySpec macKey = new SecretKeySpec(MACkey, "HmacSHA1");
		mac.init(macKey);
		mac.update(iv);
		mac.update(onlyText);
		byte[] authKey = mac.doFinal();
		
		//Ensure keys match
		if(Arrays.equals(authKey, encAuthKey))
		{
			//Decrypt ciphertext
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
			byte[] decrypted = cipher.doFinal(onlyText);
			
			//Write to new file without .enc and remove encrypted file
			if (fileName.endsWith(".enc")) 
			{	
				String decFilePath = path.toString();
				decFilePath = decFilePath.substring(0, decFilePath.length() - 4);
				File decFile = new File(decFilePath);
				FileOutputStream fos = new FileOutputStream(decFile);
				fos.write(decrypted);
				File file = newPath.toFile();
				file.delete();
				
				fos.close();
			}
		} 
		else 
		{
			throw new InvalidKeyException();
		}
		
		
	}

}


