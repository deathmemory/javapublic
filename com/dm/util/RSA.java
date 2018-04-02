package com.dm.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA {
	
	public static String[] InitRsa(){
		
		String[] rsa = new String[2];

        KeyPairGenerator kpg;
		try {
			kpg = KeyPairGenerator.getInstance("RSA");
	        kpg.initialize(1024, new SecureRandom());
	        KeyPair clsKeyPair = kpg.generateKeyPair();
	        
//			PrivateKeyInfo privKeyInfo = new PrivateKeyInfo(ASN1Sequence.getInstance(privKey.getEncoded()‌​)); 
//	    	byte[] pkcs1 = privKeyInfo.parsePrivateKey().toASN1Primitive().getEncoded()‌​;
	    	
	        rsa[0] = DMBase64.encode64(clsKeyPair.getPrivate().getEncoded());
	        rsa[1] = DMBase64.encode64(clsKeyPair.getPublic().getEncoded());
	        
//	        https://zhidao.baidu.com/question/1174806946543257659.html

		} catch (Exception e) {
			rsa = null;
		}
		
		return rsa;
	}
	
	public static byte[] EncodeRsa(byte[] arg9, String arg10){
		byte[] v0_2 = null;
		int v7 = 117;
		if (arg9 != null && arg10 != null) {
			PublicKey v0;
			try {
				v0 = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(DMBase64.decode64(arg10)));
				
				Cipher v3 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
				v3.init(1, ((Key) v0));
				int v4 = arg9.length;
				ByteArrayOutputStream v5 = new ByteArrayOutputStream();
				int v1 = 0;
				int v0_1 = 0;
				while (v4 - v0_1 > 0) {
					v0_2 = v4 - v0_1 > v7 ? v3.doFinal(arg9, v0_1, v7) : v3.doFinal(arg9, v0_1, v4 - v0_1);
					v5.write(v0_2, 0, v0_2.length);
					v0_1 = v1 + 1;
					int v8 = v0_1;
					v0_1 *= 117;
					v1 = v8;
				}
	
				v0_2 = v5.toByteArray();
				v5.close();				
			} catch (Exception e) {
				e.printStackTrace();
			}

			return v0_2;
		}

		throw new NullPointerException("encryptByPublicKey data||publicKey is null");
	}
	
	public static byte[] DecodeRsa(byte[] arg9, String arg10)throws InvalidKeySpecException, NoSuchAlgorithmException,NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
		byte[] v0 = null;
		int v7 = 128;
		if (arg10 == null) return v0;

		PrivateKey v0_1 = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(DMBase64.decode64(arg10)));
		Cipher v3 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		v3.init(2, ((Key) v0_1));
		int v4 = arg9.length;
		ByteArrayOutputStream v5 = new ByteArrayOutputStream();
		int v1 = 0;
		int v0_2 = 0;
		while (v4 - v0_2 > 0) {
			v0 = v4 - v0_2 > v7 ? v3.doFinal(arg9, v0_2, v7) : v3.doFinal(arg9,	v0_2, v4 - v0_2);
			v5.write(v0, 0, v0.length);
			v0_2 = v1 + 1;
			int v8 = v0_2;
			v0_2 *= 128;
			v1 = v8;
		}
		v0 = v5.toByteArray();
		v5.close();
		return v0;
	}
}
