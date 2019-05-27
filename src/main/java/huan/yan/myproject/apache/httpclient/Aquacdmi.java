package huan.yan.myproject.apache.httpclient;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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

    private static final String accessKeyId = "000059370018FC6007DDDA9FE1E1B34DBC991AF5A92AAF6C89";
    private static final String cdmi_server_prefix = "http://172.16.20.155:8080/aqua/rest/cdmi/";
    private static String testObject = "mytest1.txt";
    private static final String secretaccesskey = "ff0579a81257f6d9daac6e35614d09f71ae33b2bc9840bc15e6d7ede7625";

    /*static {
        testObject = UUID.randomUUID().toString().replace("-","");
    }*/

    @Test
    public void postObjetc() {
        try {
            long start0 = System.currentTimeMillis();
            HttpClient client = new HttpClient();
            HttpPost httpput = new HttpPost(cdmi_server_prefix + testObject);

            Map data = new HashMap();
            Map cdmi_job_action_params = new HashMap();
            data.put("metadata", new HashMap<>());
            data.put("mimetype", "text/plain");
            data.put("value", "This is the Value of this Data Object");

//            StringEntity reqEntity = new StringEntity("{\"metadata\": {\"cdmi_job_priority\":\"NORMAL\", \"cdmi_job_state\":\"Start\", \"cdmi_job_action\":\"mapreduce_locationcheck\", \"cdmi_job_action_params\":[\"http://172.16.20.131:8080/aqua/rest/cdmi/job20140805out\", \"http://172.16.20.131:8080/aqua/rest/cdmi/default/xinguoxian/XORMedia/spaces/vehicles/\", \"0.0\", \"0.0\", \"150.0\", \"100.0\", \"*\", \"*\"]}}");
            StringEntity reqEntity = new StringEntity(JSON.toJSONString(data));
            System.out.println(JSON.toJSONString(data));
            reqEntity.setChunked(false);
            httpput.setEntity(reqEntity);
//            httpput.addHeader("Content-Type", "application/cdmi-job");
            httpput.addHeader("Content-Type", "application/json");
            httpput.addHeader("Accept", "application/json");
            long currenttime = System.currentTimeMillis();

            String canonicalString = "POST" + "\n"
                    + "application/json" + "\n"
//                    + "\n"
                    + currenttime + "\n"
                    + "/aqua/rest/cdmi/" + testObject;
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

    @Test
    public void getObject() {
        try {
            long start0 = System.currentTimeMillis();
            HttpClient client = new HttpClient();
            HttpGet httpGet = new HttpGet(cdmi_server_prefix + testObject);
            httpGet.setHeader("x-aqua-asProxy", "true");
            httpGet.setHeader("x-aqua-read-reference-redirect", "false");
            httpGet.setHeader("X-CDMI-Specification-Version", "1.0");
            httpGet.addHeader("Content-Type", "application/cdmi-job");
            httpGet.addHeader("Accept", "application/cdmi-job");
            long currenttime = System.currentTimeMillis();

            String canonicalString = "GET" + "\n"
                    + "application/cdmi-job" + "\n"
//                    + "" + "\n"
                    + currenttime + "\n"
                    + "/aqua/rest/cdmi/" + testObject;

            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(secretaccesskey.getBytes("UTF-8"), "HmacSHA1"));
            String signature = Base64.encodeBase64String(mac.doFinal(canonicalString.getBytes("UTF-8")));
            httpGet.addHeader("Authorization", "AQUA " + accessKeyId + ":" + signature);
            httpGet.addHeader("x-aqua-date", "" + currenttime);


            HttpResponse response = client.getClient().execute(httpGet);
            System.out.println("--------------------------------------------");
            HttpEntity entity = response.getEntity();
            InputStream in = response.getEntity().getContent();
           /* String str= EntityUtils.toString(entity, "utf-8");
            System.out.println("服务器的响应是:"+str);
            System.out.println("--------------------------------------------");*/
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {

                FileOutputStream fos = new FileOutputStream(new File("d:/1.a.a.rmvb"));

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
