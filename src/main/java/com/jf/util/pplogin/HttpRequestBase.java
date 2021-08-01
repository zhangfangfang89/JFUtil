package com.jf.util.pplogin;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class HttpRequestBase {
    private static CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    public static JSONObject doGetNoParams(String url) {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        JSONObject jsonObject = null;
        try {
            response = httpClient.execute(httpGet);
            System.out.println(response);
            HttpEntity responseEntity = response.getEntity();
            System.out.println(responseEntity);
            if (responseEntity != null) {
                jsonObject = JSONObject.parseObject(responseEntity.toString());
                System.out.println(jsonObject);
                return jsonObject;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

}
