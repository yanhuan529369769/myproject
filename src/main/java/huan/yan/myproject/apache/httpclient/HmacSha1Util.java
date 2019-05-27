/**
 * 
 */
package huan.yan.myproject.apache.httpclient;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;


/**
 * It is the utility class for HMAC-SHA1.
 *
 */
public class HmacSha1Util {
  private static String URIPREFIX = "http://10.50.5.170:8080";
  
  /**
   * Convert byte array to hex string.
   * 
   * @param bytes
   * @return
   */
  private static String toHexString(byte[] bytes) {
    Formatter formatter = new Formatter();
    for (byte b : bytes) {
      formatter.format("%02x", b);
    }
    return formatter.toString();
  }
  
  /**
   * Produce new SecretAccessKey.
   * 
   * @return
   * @throws Exception
   */
  public static String produceSecretAccessKey() throws NoSuchAlgorithmException {
    KeyGenerator generator = KeyGenerator.getInstance("HMACSHA1");
    generator.init(240);
    byte[] awsSecretAccessKey = generator.generateKey().getEncoded();
    return toHexString(awsSecretAccessKey);
  }
  
  /**
   * Get  the signature of HMAC-SHA1.
   * 
   * @param secretAccessKey
   * @param canonicalString
   * @return
   */
  public static String signWithHmacSha1(String secretAccessKey, String canonicalString) throws Exception {
    Mac mac = Mac.getInstance("HmacSHA1");
    mac.init(new SecretKeySpec(secretAccessKey.getBytes("UTF-8"), "HmacSHA1"));
    return Base64.encodeBase64String(mac.doFinal(canonicalString.getBytes("UTF-8")));
  }
  
  public static String uriUTF8Encode(String requestURI) {
    if(requestURI == null) return null;
    if(requestURI.indexOf("%") != -1) return requestURI;
    
    String urlEncoded = requestURI;
    try {
      String urlStr = URIPREFIX + requestURI;
      URL url = new URL(urlStr);
      URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
      urlEncoded = uri.toASCIIString();
      urlEncoded = urlEncoded.substring(URIPREFIX.length());
    } catch (Throwable ex) {
      urlEncoded = requestURI;
    }
    return urlEncoded;
  }


  public static void main(String[] args) {
    try {
      //String id = ObjectIDForCDMI.getObjectIDForXOR(AquaObjectType.USER);
      //String id = "00005937001801AB07905B547618084AA68E4627F8CD7A9B44";
      String id = "0000593700187C640729B1A46A980244EB965285E7A89379F3";
      System.out.println("id: [" + id + "]");
      
      //String secretaccesskey = HmacSha1Util.produceSecretAccessKey();
      //String secretaccesskey = "218910d09c2125e5175363061fd1bc21b9f0fc7db10e8d37caf843e5f248";
      String secretaccesskey = "fd558285953d5e4cd03a5296aeffbef95e6154e5884b36e87212842ae71e";
      //for root user
      //String id = "000059370018169B0722EC4CFA3F4F405BAB572D86940B5245";
      //String secretaccesskey = "4d66679b857469724e084e1874b66e1a62df1bae6e1d0d5841995f2ebfe7";
      //~
      System.out.println("SecretAccessKey: [" + secretaccesskey + "]");
      
      //long currenttime = System.currentTimeMillis() - (1363686689005L - 1363546222948L);
      //long currenttime = System.currentTimeMillis();
      long currenttime = System.currentTimeMillis();
      System.out.println("CurrentTime: [" + currenttime + "]");
      String contentType = "application/cdmi-object";
      //String contentType = "application/octet-stream";
      String http_request_uri = "/aqua/rest/cdmi/demo5/test/DSC_037test.jpg";
      //String http_request_uri = "/aqua/rest/cdmi/TestObject7uk96457";

      String canonicalString = "GET" + "\n"
                                + contentType + "\n"
                                + currenttime + "\n"
                                + http_request_uri;

      /*
      String canonicalString = "PUT" + "\n"
                                + contentType + "\n"
                                + currenttime + "\n"
                                + http_request_uri;
                                */
      long start = System.currentTimeMillis();
      String signature = HmacSha1Util.signWithHmacSha1(secretaccesskey, canonicalString);
      long end = System.currentTimeMillis();
      System.out.println("Spent time: [" + (end-start) + "] ms");
      System.out.println("Signature: [" + signature + "]");
      System.out.println("Authorization: AQUA "+id+":"+signature);
      System.out.println("x-aqua-date: "+currenttime);
      System.out.println("x-aqua-user-domain-uri: /cdmi_domains/defaultdomainname/");
      
      String aa = uriUTF8Encode("/aqua/rest/cdmi/用户头像/aa");
      System.out.println("Encoded URI:"+aa);
      aa = uriUTF8Encode("/aqua/rest/cdmi/%E7%94%A8%E6%88%B7%E5%A4%B4%E5%83%8F/aa");
      System.out.println("Encoded URI:"+aa);
      aa = uriUTF8Encode("/aqua");
      System.out.println("Encoded URI:"+aa);
      aa = uriUTF8Encode("/");
      System.out.println("Encoded URI:"+aa);
      aa = uriUTF8Encode("");
      System.out.println("Encoded URI:"+aa);
      
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

}
