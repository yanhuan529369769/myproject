package huan.yan.myproject.apache.httpclient;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest


public class Aquacdmi {

    public static void main(String[] args) {
        Aquacdmi aquacdmi=new Aquacdmi();
        aquacdmi.postContainer();
        aquacdmi.postObjetcCDMIType();
        aquacdmi.getObjectCDMIType();
        aquacdmi.postObjetc();
        aquacdmi.getObject();
    }

    private static final String accessKeyId = "000059370018FC6007DDDA9FE1E1B34DBC991AF5A92AAF6C89";
    private static final String cdmi_server_prefix = "http://172.16.20.155:8080/aqua/rest/cdmi/";
    private static String testObject = "final123.txt";
    private static String testContainer = "myContainer123/";
    private static final String secretaccesskey = "ff0579a81257f6d9daac6e35614d09f71ae33b2bc9840bc15e6d7ede7625";

    /*static {
        testObject = UUID.randomUUID().toString().replace("-","");
    }*/


    public void postContainer() {
        try {
            long start0 = System.currentTimeMillis();
            HttpClient client = new HttpClient();
            HttpPut httpput = new HttpPut(cdmi_server_prefix + testContainer);
            Map data = new HashMap();
//            data.put("move","true");
            StringEntity reqEntity = new StringEntity(JSON.toJSONString(data));
            System.out.println(JSON.toJSONString(data));
            reqEntity.setChunked(false);
            httpput.setEntity(reqEntity);
            httpput.addHeader("Content-Type", "application/cdmi-container");
            httpput.addHeader("Accept", "application/cdmi-container");
            httpput.addHeader("X-CDMI-Specification-Version", "1.0.2");
            long currenttime = System.currentTimeMillis();
            String canonicalString = "PUT" + "\n"
                    + "application/cdmi-container" + "\n"
//                    + "\n"
                    + currenttime + "\n"
                    + "/aqua/rest/cdmi/" + testContainer;
            String signature = HmacSha1Util.signWithHmacSha1(secretaccesskey, canonicalString);
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(secretaccesskey.getBytes("UTF-8"), "HmacSHA1"));
//            String signature = Base64.encodeBase64String(mac.doFinal(canonicalString.getBytes("UTF-8")));
            httpput.addHeader("Authorization", "AQUA " + accessKeyId + ":" + signature);
            httpput.addHeader("x-aqua-date", "" + currenttime);
            System.out.println("Authorization: AQUA " + accessKeyId + ":" + signature);
            System.out.println("x-aqua-date: " + currenttime);
            System.out.println("executing request " + httpput.getRequestLine());

            HttpResponse response = client.getClient().execute(httpput);


            StatusLine respStatusLine = response.getStatusLine();
            int statuscode = respStatusLine.getStatusCode();
            System.out.println("[0] status code [" + statuscode + "]");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public void postObjetcCDMIType() {
        try {
            long start0 = System.currentTimeMillis();
            HttpClient client = new HttpClient();
            HttpPut httpput = new HttpPut(cdmi_server_prefix +testContainer+ testObject);
            Map data = new HashMap();
            Map metadata = new HashMap();
            metadata.put("name","yan");
            data.put("metadata", metadata);
            data.put("mimetype", "text/plain");
            data.put("value", "cdmi type");
//            data.put("domainURI", "/cdmi_domains/MyDomain/");
            StringEntity reqEntity = new StringEntity(JSON.toJSONString(data));
            System.out.println(JSON.toJSONString(data));
            reqEntity.setChunked(false);
            httpput.setEntity(reqEntity);
            httpput.addHeader("Content-Type", "application/cdmi-object");
            httpput.addHeader("Accept", "application/cdmi-object");
            httpput.addHeader("X-CDMI-Specification-Version", "1.0.2");
            long currenttime = System.currentTimeMillis();

            String canonicalString = "PUT" + "\n"
                    + "application/cdmi-object" + "\n"
                    + currenttime + "\n"
                    + "/aqua/rest/cdmi/" +testContainer+ testObject;
            String signature = HmacSha1Util.signWithHmacSha1(secretaccesskey, canonicalString);
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(secretaccesskey.getBytes("UTF-8"), "HmacSHA1"));
            httpput.addHeader("Authorization", "AQUA " + accessKeyId + ":" + signature);
            httpput.addHeader("x-aqua-date", "" + currenttime);
            System.out.println("Authorization: AQUA " + accessKeyId + ":" + signature);
            System.out.println("x-aqua-date: " + currenttime);
            System.out.println("executing request " + httpput.getRequestLine());

            HttpResponse response = client.getClient().execute(httpput);
            System.out.println(response);
            long end0 = System.currentTimeMillis();
            StatusLine respStatusLine = response.getStatusLine();
            int statuscode = respStatusLine.getStatusCode();
            System.out.println("[0] status code [" + statuscode + "] spent [" + (end0 - start0) + "] ms");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public void getObjectCDMIType() {
        try {
            long start0 = System.currentTimeMillis();
            HttpClient client = new HttpClient();
            HttpGet httpGet = new HttpGet(cdmi_server_prefix +testContainer+ testObject);
            httpGet.setHeader("x-aqua-asProxy", "true");
            httpGet.setHeader("x-aqua-read-reference-redirect", "false");
            httpGet.setHeader("X-CDMI-Specification-Version", "1.0.2");
            httpGet.addHeader("Content-Type", "application/cdmi-object");
            httpGet.addHeader("Accept", "application/cdmi-object");
            long currenttime = System.currentTimeMillis();

            String canonicalString = "GET" + "\n"
                    + "application/cdmi-object" + "\n"
//                    + "" + "\n"
                    + currenttime + "\n"
                    + "/aqua/rest/cdmi/" +testContainer+ testObject;

            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(secretaccesskey.getBytes("UTF-8"), "HmacSHA1"));
            String signature = Base64.encodeBase64String(mac.doFinal(canonicalString.getBytes("UTF-8")));
            httpGet.addHeader("Authorization", "AQUA " + accessKeyId + ":" + signature);
            httpGet.addHeader("x-aqua-date", "" + currenttime);

            HttpResponse response = client.getClient().execute(httpGet);
            System.out.println("--------------------------------------------");
            HttpEntity entity = response.getEntity();
            System.out.println(response);
            System.out.println(entity);
            String str= EntityUtils.toString(entity, "utf-8");
            System.out.println("服务器的响应是:"+str);
            System.out.println("--------------------------------------------");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }





    public void postObjetc() {
        try {
            long start0 = System.currentTimeMillis();
            HttpClient client = new HttpClient();
            HttpPut httpput = new HttpPut(cdmi_server_prefix + testContainer+testObject);
            Map data = new HashMap();
            data.put("value", "This is the Value of this Data Object AAAAAAAAAAAAAAAAAAAAAAA");

//            StringEntity reqEntity = new StringEntity("{\"metadata\": {\"cdmi_job_priority\":\"NORMAL\", \"cdmi_job_state\":\"Start\", \"cdmi_job_action\":\"mapreduce_locationcheck\", \"cdmi_job_action_params\":[\"http://172.16.20.131:8080/aqua/rest/cdmi/job20140805out\", \"http://172.16.20.131:8080/aqua/rest/cdmi/default/xinguoxian/XORMedia/spaces/vehicles/\", \"0.0\", \"0.0\", \"150.0\", \"100.0\", \"*\", \"*\"]}}");
            StringEntity reqEntity = new StringEntity(JSON.toJSONString(data));
            System.out.println(JSON.toJSONString(data));
            reqEntity.setChunked(false);
            httpput.setEntity(reqEntity);
//            httpput.addHeader("Content-Type", "application/cdmi-job");
            httpput.addHeader("Content-Type", "text/plain");
            httpput.addHeader("Accept", "text/plain");

            long currenttime = System.currentTimeMillis();

            String canonicalString = "PUT" + "\n"
                    + "text/plain" + "\n"
//                    + "\n"
                    + currenttime + "\n"
                    + "/aqua/rest/cdmi/" +testContainer+ testObject;
            String signature = HmacSha1Util.signWithHmacSha1(secretaccesskey, canonicalString);
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(secretaccesskey.getBytes("UTF-8"), "HmacSHA1"));
//            String signature = Base64.encodeBase64String(mac.doFinal(canonicalString.getBytes("UTF-8")));
            httpput.addHeader("Authorization", "AQUA " + accessKeyId + ":" + signature);
            httpput.addHeader("x-aqua-date", "" + currenttime);
            System.out.println("Authorization: AQUA " + accessKeyId + ":" + signature);
            System.out.println("x-aqua-date: " + currenttime);
            System.out.println("executing request " + httpput.getRequestLine());

            HttpResponse response = client.getClient().execute(httpput);

            long end0 = System.currentTimeMillis();
            StatusLine respStatusLine = response.getStatusLine();
            int statuscode = respStatusLine.getStatusCode();
            System.out.println("[0] status code [" + statuscode + "] spent [" + (end0 - start0) + "] ms");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public void getObject() {
        try {
            long start0 = System.currentTimeMillis();
            HttpClient client = new HttpClient();
            HttpGet httpGet = new HttpGet(cdmi_server_prefix +testContainer+ testObject);
            long currenttime = System.currentTimeMillis();

            String canonicalString = "GET" + "\n"
//                    + "application/json" + "\n"
                    + "" + "\n"
                    + currenttime + "\n"
                    + "/aqua/rest/cdmi/" +testContainer+ testObject;

            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(secretaccesskey.getBytes("UTF-8"), "HmacSHA1"));
            String signature = Base64.encodeBase64String(mac.doFinal(canonicalString.getBytes("UTF-8")));
            httpGet.addHeader("Authorization", "AQUA " + accessKeyId + ":" + signature);
            httpGet.addHeader("x-aqua-date", "" + currenttime);
            HttpResponse response = client.getClient().execute(httpGet);
            System.out.println("--------------------------------------------");
            HttpEntity entity = response.getEntity();
            InputStream in = response.getEntity().getContent();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {

                FileOutputStream fos = new FileOutputStream(new File("d:/cdmi.txt"));

                bis = new BufferedInputStream(in);
                bos = new BufferedOutputStream(fos);
                byte[] buffer = new byte[1024 * 1024];
                int readCount;
                while ((readCount = bis.read(buffer)) >= 0) {
                    if (readCount > 0) {
                        bos.write(buffer, 0, readCount);
                    }
                }


                long end0 = System.currentTimeMillis();
                StatusLine respStatusLine = response.getStatusLine();
                int statuscode = respStatusLine.getStatusCode();
                System.out.println("[0] status code [" + statuscode + "] spent [" + (end0 - start0) + "] ms");
            } catch (Exception e) {
                e.printStackTrace();


            } finally {
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
