package com.dm.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import com.dm.util.ssl.TrustAnyHostnameVerifier;
import com.dm.util.ssl.TrustAnyTrustManager;

public class HttpRequest {
    /**
     * send get method
     * 
     * @param url
     * @param param
     * @return response string
     */
	
	public static String sendGet(URLConnection connection, String param) {
		String result = "";
        BufferedReader in = null;
        try {
            
            // do connect
            connection.connect();
            // get header fields
            Map<String, List<String>> map = connection.getHeaderFields();
            
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // use BufferedReader to read input stream
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("send get exception: " + e);
            e.printStackTrace();
        }
        // use finally to close 
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
	}
	
    public static String sendGet(String url, String param) {
    	try {
    		String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // open connection
            URLConnection connection = realUrl.openConnection();
            // set request property
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            return sendGet(connection, param);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	return null;
    }

    /**
     * 閸氭垶瀵氱�癸拷 URL 閸欐垿锟戒赋OST閺傝纭堕惃鍕嚞濮癸拷
     * 
     * @param url
     *            閸欐垿锟戒浇顕Ч鍌滄畱 URL
     * @param param
     *            鐠囬攱鐪伴崣鍌涙殶閿涘矁顕Ч鍌氬棘閺佹澘绨茬拠銉︽Ц name1=value1&name2=value2 閻ㄥ嫬鑸板蹇嬶拷锟�
     * @return 閹碉拷娴狅綀銆冩潻婊呪柤鐠у嫭绨惃鍕惙鎼存梻绮ㄩ弸锟�
     */
    
    public static String sendPost(URLConnection conn, Object param) {
    	OutputStream out = null;
        BufferedReader in = null;
        String result = "";
        try {
        	// if https
        	if (conn instanceof HttpsURLConnection)  {
            	SSLContext sc = SSLContext.getInstance("SSL");  
                sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
                ((HttpsURLConnection) conn).setSSLSocketFactory(sc.getSocketFactory());  
                ((HttpsURLConnection) conn).setHostnameVerifier(new TrustAnyHostnameVerifier());  
            }	
//        	PrintWriter out = new PrintWriter(conn.getOutputStream());
        	out = conn.getOutputStream();
        	byte[] buffer = null;
            // 閸欐垿锟戒浇顕Ч鍌氬棘閺侊拷
            if (param instanceof String) {
//            	out.print((String)param);	
            	buffer = ((String)param).getBytes();
			}
            else {
//            	char c[] = StringUtil.Bytes2Chars((byte[])param);
//            	out.print(c);
            	buffer = (byte[])param;
            }
            out.write(buffer); 
            out.flush();
            
            String ContentEncoding = conn.getHeaderField("Content-Encoding");
            if ("gzip".equalsIgnoreCase(ContentEncoding)) {
            	ByteArrayOutputStream out0 = new ByteArrayOutputStream();  
//                ByteArrayInputStream in0 = new ByteArrayInputStream(bytes);  
                try {  
                    GZIPInputStream ungzip = new GZIPInputStream(conn.getInputStream());  
                    byte[] buffer0 = new byte[256];  
                    int n;  
                    while ((n = ungzip.read(buffer0)) >= 0) {  
                        out0.write(buffer0, 0, n);  
                    }  
                } catch (Exception e) {  
                    System.out.println("gzip uncompress error: " + e);  
                }  
                result = new String (out0.toByteArray());
			}
            else {
            	in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
            }
            
        } catch (Exception e) {
            System.out.println("post exception: "+e);
            e.printStackTrace();
        }
        finally{
            try{
//                if(out!=null){
//                    out.close();
//                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    
    public static String sendPost(String url, Object param) {
    	String result = "";
		try {
			URL realUrl = new URL(url);
	        URLConnection conn;
			conn = realUrl.openConnection();
	        conn.setRequestProperty("accept", "*/*");
	        conn.setRequestProperty("connection", "Keep-Alive");
//	        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
	        conn.setRequestProperty("user-agent",
	                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        
	        result = sendPost(conn, param);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return result;
    }    
    
    public static byte[] sendRequest(String url, byte[] data, int timeout) {
    	byte[] rets = null;
    	try {
    		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            if(data == null) {
                conn.setRequestMethod("GET");
            }
            else {
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Length", "" + data.length);
                conn.setRequestProperty("Content-Type", "application/octet-stream");
                conn.setRequestProperty("Charset", "UTF-8");
                OutputStream outstream = conn.getOutputStream();
                outstream.write(data);
                outstream.close();
            }
            
            int response = ((HttpURLConnection)conn).getResponseCode();
            if (200 == response) {
            	ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                InputStream in = ((HttpURLConnection)conn).getInputStream();
                byte[] retBufTmp = new byte[1024];
                while(true) {
                    int readed = in.read(retBufTmp, 0, 1024);
                    if(readed == -1) {
                        break;
                    }

                    outstream.write(retBufTmp, 0, readed);
                }
                
                rets = outstream.toByteArray();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return rets;
    }   
}