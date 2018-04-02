package com.dm.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DMAES {
	public static byte[] AES_CBC_Encrypt(byte[] content, byte[] keyBytes, byte[] iv){  
        
        try{  
        	SecretKeySpec skp = new SecretKeySpec(keyBytes, "AES");
//            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");  
//            keyGenerator.init(128, new SecureRandom(keyBytes));  
//            SecretKey key=keyGenerator.generateKey();  
        	
            Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");  
            cipher.init(Cipher.ENCRYPT_MODE, skp, new IvParameterSpec(iv));  
            byte[] result=cipher.doFinal(content);  
            return result;  
        }catch (Exception e) {  
            // TODO Auto-generated catch block  
            System.out.println("exception:"+e.toString());  
        }   
        return null;  
    }  
      
    public static byte[] AES_CBC_Decrypt(byte[] content, byte[] keyBytes, byte[] iv){  
          
        try{  
//            KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");  
//            keyGenerator.init(128, new SecureRandom(keyBytes));//key长可设为128，192，256位，这里只能设为128  
//            SecretKey key=keyGenerator.generateKey();
        	SecretKeySpec skp = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");  
            cipher.init(Cipher.DECRYPT_MODE, skp, new IvParameterSpec(iv));  
            byte[] result=cipher.doFinal(content);  
            return result;  
        }catch (Exception e) {  
            System.out.println("exception:"+e.toString());  
        }   
        return null;  
    }  
}
