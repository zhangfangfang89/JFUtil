package com.jf.util.common;


import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.xsom.impl.scd.Iterators;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

public class HttpRequestBase {
    private static CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    public static String doGet(String url, Map<String,String> params,Map<String,String>headerM) throws URISyntaxException {
        String contentType = (String) headerM.get("Content-Type");
        URIBuilder uriBuilder = new URIBuilder(url);
        String string = "";
        if ("application/json;charset=UTF-8".equals(contentType)){
            System.out.println("application/json;charset=UTF-8");
            //json的传参方式
        }else{
            System.out.println("application/x-www-form-urlencoded; charset=utf-88");
            Iterator intr = params.entrySet().iterator();
            while (intr.hasNext()){
                Map.Entry entry = (Map.Entry)intr.next();
                String k = (String)entry.getKey();
                String v = (String) entry.getValue();
                uriBuilder.addParameter(k,v);
            }
        }
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        //处理请求头
        Iterator intrH = headerM.entrySet().iterator();
        while (intrH.hasNext()){
            Map.Entry entry = (Map.Entry)intrH.next();
            String k = (String)entry.getKey();
            String v = (String) entry.getValue();
            httpGet.setHeader(new BasicHeader(k, v));
        }
        CloseableHttpResponse response = null;
        JSONObject jsonObject = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() ==200) {
                string = EntityUtils.toString(responseEntity);
            }

        } catch (ClientProtocolException e) {
            string = "fail";

        } catch (IOException e) {
            string = "fail";
        } catch (IllegalStateException e){
            string = "fail";
//        }finally {
//            try {
//                // 释放资源
//                if (httpClient != null) {
//                    httpClient.close();
//                }
//                if (response != null) {
//                    response.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        return string;
    }

    public static JSONObject doGetJSON(String url, Map<String,String> params,Map<String,String>headerM) throws URISyntaxException {
        String contentType = (String) headerM.get("Content-Type");
        if (Objects.isNull(contentType)){
            contentType="58";
        }
        URIBuilder uriBuilder = new URIBuilder(url);

        if (contentType.equals("application/json;charset=UTF-8")){
            System.out.println("application/json;charset=UTF-8");
          //json的传参方式
        }else{
            System.out.println("application/x-www-form-urlencoded; charset=utf-88");
            Iterator intr = params.entrySet().iterator();
            while (intr.hasNext()){
                Map.Entry entry = (Map.Entry)intr.next();
                String k = (String)entry.getKey();
                String v = (String) entry.getValue();
                uriBuilder.addParameter(k,v);
            }
        }
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        //处理请求头
        Iterator intrH = headerM.entrySet().iterator();
        while (intrH.hasNext()){
            Map.Entry entry = (Map.Entry)intrH.next();
            String k = (String)entry.getKey();
            String v = (String) entry.getValue();
            httpGet.setHeader(new BasicHeader(k, v));
        }
        CloseableHttpResponse response = null;
        JSONObject jsonObject = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            System.out.println(responseEntity);
            if (response.getStatusLine().getStatusCode() ==200) {
                String string = EntityUtils.toString(responseEntity);
                jsonObject = JSONObject.parseObject(string);
                System.out.println(jsonObject);
                return jsonObject;
            }
        } catch (ClientProtocolException e) {
            return jsonObject;
        } catch (IOException e) {
            return jsonObject;
        }
        return jsonObject;
    }
    public static JSONObject doPostJSON(String url, Map<String,String> params,Map<String,String>headerM) throws URISyntaxException, UnsupportedEncodingException {
        String contentType = (String) headerM.get("Content-Type");
        if (Objects.isNull(contentType)){
            contentType="58";
        }
        HttpPost httpPost = new HttpPost(url);
        UrlEncodedFormEntity formEntity = null;
        //处理请求头
        Iterator intrH = headerM.entrySet().iterator();
        while (intrH.hasNext()){
            Map.Entry entry = (Map.Entry)intrH.next();
            String k = (String)entry.getKey();
            String v = (String) entry.getValue();
            httpPost.setHeader(new BasicHeader(k, v));
        }
        if (contentType.equals("application/json;charset=UTF-8")){
            System.out.println("application/json;charset=UTF-8");


        }else {
            List<NameValuePair> paramEntity = new ArrayList<>();

            Iterator intr = params.entrySet().iterator();
            while (intr.hasNext()) {
                Map.Entry entry = (Map.Entry) intr.next();
                String k = (String) entry.getKey();
                String v = (String) entry.getValue();
                paramEntity.add(new BasicNameValuePair(k, v));
            }
            formEntity = new UrlEncodedFormEntity(paramEntity);
        }
        httpPost.setEntity(formEntity);
        CloseableHttpResponse response = null;
        JSONObject jsonObject = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            System.out.println(responseEntity);
            if (response.getStatusLine().getStatusCode() ==200) {
                String string = EntityUtils.toString(responseEntity);
                jsonObject = JSONObject.parseObject(string);
                System.out.println(jsonObject);
                return jsonObject;
            }
        } catch (ClientProtocolException e) {
            return jsonObject;
        } catch (IOException e) {
            return jsonObject;
        }
        return jsonObject;
    }

    public static void main(String[] args) throws URISyntaxException {
        HashMap<String,String> p = new HashMap<>();
        HashMap<String,String> h = new HashMap<>();



    }
}
