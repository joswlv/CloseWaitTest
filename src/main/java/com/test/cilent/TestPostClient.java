package com.test.cilent;

/**
 * Created by Jo_seungwan on 2017. 4. 17..
 */

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class TestPostClient {
    @Test
    public void test() {
        String url = "http://10.161.241.152:8080/v1/video/get_attributes_data";
        int timeout = 10;
        HttpPost req = new HttpPost(url);
        StringEntity params = null;

        try {
            params = new StringEntity("begin:0,end:50,uid:\"G7QMAJEE6IHVIFE08AFU2W2CA\"");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        req.addHeader("content-type", "application/json");
        req.addHeader("Accept","application/json");
        req.setEntity(params);
        RequestConfig config = RequestConfig.custom()
                .setSocketTimeout(timeout)
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .build();
        req.setConfig(config);
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(1);
        CloseableHttpClient client = HttpClients.createMinimal(connManager);

        for (int i = 0; i < 1000; i++) {
            CloseableHttpResponse rep = null;
            HttpEntity entity = null;
            try {
                rep = client.execute(req);
                entity = rep.getEntity();
                System.out.println(EntityUtils.toString(entity));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            } finally {
                try {
                    EntityUtils.consume(entity);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if (rep != null) {
                    try {
                        rep.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
