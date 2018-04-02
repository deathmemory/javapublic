package com.dm.util;


import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public final class MD5 {
    public static String md5(InputStream arg4) {
        String v0_2;
        MessageDigest v0_1;
        byte[] v1 = new byte[1024];
        try {
            v0_1 = MessageDigest.getInstance("MD5");
            while(true) {
                int v2 = arg4.read(v1, 0, 1024);
                if(v2 == -1) {
                    break;
                }

                v0_1.update(v1, 0, v2);
            }

            arg4.close();
            v0_2 = new BigInteger(1, v0_1.digest()).toString(16);
            if(v0_2.length() == 32) {
                return v0_2;
            }
        }
        catch(Exception v0) {
            v0_2 = null;
        }

        return v0_2;
    }

    public static String md5(byte[] arg10) {
        String v0_2;
        int v9 = 16;
        int v0 = 0;
        String v1 = null;
        char[] v3 = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest v2 = MessageDigest.getInstance("MD5");
            v2.update(arg10);
            byte[] v4 = v2.digest();
            char[] v5 = new char[32];
            int v2_1 = 0;
            while(v0 < v9) {
                int v6 = v4[v0];
                int v7 = v2_1 + 1;
                v5[v2_1] = v3[v6 >>> 4 & 15];
                v2_1 = v7 + 1;
                v5[v7] = v3[v6 & 15];
                ++v0;
            }

            v0_2 = new String(v5);
        }
        catch(Exception v0_1) {
            v0_2 = v1;
        }

        return v0_2.toUpperCase();
    }
    
    public static String md5_32(byte[] arg10) {
    	return md5(arg10);
    }
    
    public static String md5_32(String arg) {
    	return md5_32(arg.getBytes());
    }
    
    public static String md5_16(byte[] arg10) {
    	return md5_32(arg10).substring(8, 24);
    }
    
    public static String md5_16(String arg10) {
    	return md5_32(arg10).substring(8, 24);
    }
}

