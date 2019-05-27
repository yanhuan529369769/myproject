package huan.yan.myproject.apache.httpclient;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpClient {

    private org.apache.http.client.HttpClient httpClient;

    public HttpClient() {
        if (httpClient == null) {
            synchronized (this.getClass()) {
                if (httpClient == null) {
                    CloseableHttpClient client = HttpClientBuilder.create().build();
                    this.httpClient = client;
                }
            }
        }
    }

    public org.apache.http.client.HttpClient getClient(){
        return httpClient;
    }

}
