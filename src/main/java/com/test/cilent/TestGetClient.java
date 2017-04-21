package com.test.cilent;

/**
 * Created by Jo_seungwan on 2017. 4. 17..
 */
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestGetClient extends Thread {

    public void run() {
        try {
            String url = "http://10.161.241.152:8080/delayRequest?delay=long";
            int timeout = 100;
            HttpGet req = new HttpGet(url);
            RequestConfig config = RequestConfig.custom()
                    .setSocketTimeout(timeout)
                    .setConnectTimeout(timeout)
                    .setConnectionRequestTimeout(timeout)
                    .build();
            req.setConfig(config);
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
            connManager.setMaxTotal(1);
            CloseableHttpClient client = HttpClients.createMinimal(connManager);

            for (int i = 0; i < 2; i++) {
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
        } catch(Exception e) {
            System.err.println(e.toString());
        }
    }

    @Test
    public void test() {

        while(true) {
            long stime = System.currentTimeMillis();
            ArrayList<Thread> threads = new ArrayList<Thread>();
            for(int i=0;i<5000;i++) {
                Thread thread = new TestGetClient();
                thread.setName("thr"+i);
                thread.start();
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {}
                threads.add(thread);
            }

            for(Thread thread : threads) {
                try {
                    thread.join();
                    System.out.println("The thread '"+thread.getName()+"' has passed!");
                } catch (InterruptedException e) {}
            }
            System.err.println((System.currentTimeMillis()-stime)/1000.0+"s");
        }
    }
}
