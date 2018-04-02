package com.dm.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Random;

public class StringUtil {
    public static String byteArrayToHexString(byte[] src){
    	
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv+" ");
        }
        return stringBuilder.toString();
    	
    }
    
    public static String byteArrayToHexView(byte[] src) {
    	StringBuilder stringBuilder = new StringBuilder();
    	
    	if (src == null || src.length <= 0) {
			return null;
		}
    	
    	for (int i = 0; i < src.length; i+= 16) {
    		int srclen = src.length - i;
    		if (srclen > 16) {
				srclen = 16;
			}
			for (int j = 0; j < srclen; j++) {
				int hb = src[i+j] & 0xFF;
				String hexstr = Integer.toHexString(hb);
				if (hexstr.length() < 2) {
					stringBuilder.append("0");					
				}
				stringBuilder.append(hexstr);
				stringBuilder.append(" ");
			}
			if (srclen < 16) {
				for (int b = 0; b < 16 - srclen; b++) {
					stringBuilder.append("   ");
				}
			}
			stringBuilder.append("    ");
			byte[] strview = new byte[16];
			
			System.arraycopy(src, i, strview, 0, srclen);
			try {
				String hexstr = new String(strview, "utf-8");
				hexstr = hexstr.replace("\r", "");
				hexstr = hexstr.replace("\n", "");
				stringBuilder.append(hexstr);
				stringBuilder.append("\r\n");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
    	return stringBuilder.toString();
    }

    public static byte[] stringToHexBytes(String str) {
    	try {
    		int len = str.length() / 2;
        	byte []res = new byte[len];
        	for (int i = 0; i < len; i++) {
        		String b = "0x" + str.substring(i * 2, (i+1) * 2 );
        		res[i] = (byte) Integer.decode(b).intValue();
    		}
        	
        	return res;
		} catch (Exception e) {
			e.printStackTrace();
		}   	
    	
    	return null;
    }
    
    /**
     * 获取 XML 标签内容
     * @param xml 输入XML字串
     * @param node	节点名称
     * @return	返回内容
     */
    public static String getXMLNode(String xml, String node) {
		String returnStr = "";
		int start = xml.indexOf("<" + node + ">");
		int end = xml.indexOf("</" + node + ">");
		if (start >= 0 && end >= 0) {
			returnStr = xml.substring(start + ("<" + node + ">").length(), end);
		}
		return returnStr;
	}
    
    // char转byte

    public static byte[] chars2Bytes (char[] chars) {
       Charset cs = Charset.forName ("UTF-8");
       CharBuffer cb = CharBuffer.allocate (chars.length);
       cb.put (chars);
                     cb.flip ();
       ByteBuffer bb = cs.encode (cb);
      
       return bb.array();

     }

    // byte转char

    public static char[] Bytes2Chars (byte[] bytes) {
    	char[] c = new char[bytes.length];
    	for (int i = 0; i < c.length; i++) {
			c[i] = (char) (bytes[i] & 0xff);
		}

    	for (int i = 0; i < c.length; i++) {
    		int tmp = c[i];
			System.out.print(tmp + " ");
		}
    	
    	return c;
//		Charset cs = Charset.forName ("UTF-8");
//		ByteBuffer bb = ByteBuffer.allocate (bytes.length);
//		bb.put (bytes);
//		bb.flip ();
//		CharBuffer cb = cs.decode (bb);      
//		return cb.array();
    }
    
    public static String getRandomString(int length){
    	String string = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";   
        StringBuffer sb = new StringBuffer();
        int len = string.length();
        for (int i = 0; i < length; i++) {
            sb.append(string.charAt(new Random().nextInt(len-1)));
        }
        return sb.toString();
    }
}
