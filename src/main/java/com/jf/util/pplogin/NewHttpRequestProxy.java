package com.jf.util.pplogin;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NewHttpRequestProxy {


    // 让connectionmanager管理httpclientconnection时是否关闭连接
    private static boolean alwaysClose = false;
    // 返回数据编码格式
    public final static String ENCODING = "UTF-8";

    private final HttpClient client = new HttpClient(new SimpleHttpConnectionManager(alwaysClose));

    public HttpClient getHttpClient() {
        return client;
    }

    /**
     * 用法： HttpRequestProxy hrp = new HttpRequestProxy();
     * hrp.doRequest("http://www.163.com",null,null,"gbk");
     *
     * @param url      请求的资源ＵＲＬ
     * @param postData POST请求时form表单封装的数据 没有时传null
     * @param header   request请求时附带的头信息(header) 没有时传null
     * @param encoding response返回的信息编码格式 没有时传null
     * @return response返回的文本数据
     * @throws Exception
     */
    public String doRequest(String url, Map<?, ?> postData, Map<?, ?> header, String encoding) throws Exception {
        String responseString = null;
        // 头部请求信息
        Header[] headers = null;
        if (header != null) {
            Set<?> entrySet = header.entrySet();
            int dataLength = entrySet.size();
            headers = new Header[dataLength];
            int i = 0;
            for (Iterator<?> itor = entrySet.iterator(); itor.hasNext(); ) {
                Map.Entry<?, ?> entry = (Map.Entry<?, ?>) itor.next();
                headers[i++] = new Header(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        // post方式
        if (postData != null) {
            PostMethod postRequest = new PostMethod(url.trim());
            if (headers != null) {
                for (int i = 0; i < headers.length; i++) {
                    postRequest.setRequestHeader(headers[i]);
                }
            }
            Set<?> entrySet = postData.entrySet();
            int dataLength = entrySet.size();
            NameValuePair[] params = new NameValuePair[dataLength];
            int i = 0;
            for (Iterator<?> itor = entrySet.iterator(); itor.hasNext(); ) {
                Map.Entry<?, ?> entry = (Map.Entry<?, ?>) itor.next();
                params[i++] = new NameValuePair(entry.getKey().toString(), entry.getValue().toString());
            }
            postRequest.setRequestBody(params);
            try {
                responseString = this.executeMethod(postRequest, encoding);
            } catch (Exception e) {
                throw e;
            } finally {
                postRequest.releaseConnection();
            }
        }
        // get方式
        if (postData == null) {
            GetMethod getRequest = new GetMethod(url.trim());
            if (headers != null) {
                for (int i = 0; i < headers.length; i++) {
                    getRequest.setRequestHeader(headers[i]);
                }
            }
            try {
                responseString = this.executeMethod(getRequest, encoding);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            } finally {
                getRequest.releaseConnection();
            }
        }
        return responseString;
    }

    private String executeMethod(HttpMethod request, String encoding) throws Exception {
        String responseContent = null;
        InputStream responseStream = null;
        BufferedReader rd = null;
        String temp = null;
        try {
            int state = this.getHttpClient().executeMethod(request);


            Header[] headers = request.getResponseHeaders();
            for (Header header : headers) {
                temp = header.getValue();
                if (temp.contains("PPU")) {
//注释掉的代码用于调试登录出不来PPU的信息

                    break;
                }

            }


            if (encoding != null) {
                responseStream = request.getResponseBodyAsStream();
                rd = new BufferedReader(new InputStreamReader(responseStream, encoding));
                String tempLine = rd.readLine();
                StringBuffer tempStr = new StringBuffer();
                String crlf = System.getProperty("line.separator");
                while (tempLine != null) {
                    tempStr.append(tempLine);
                    tempStr.append(crlf);
                    tempLine = rd.readLine();
                }
                responseContent = tempStr.toString();
            } else {
                responseContent = request.getResponseBodyAsString();
            }

            Header locationHeader = request.getResponseHeader("location");
            // 返回代码为302,301时，表示页面己经重定向，则重新请求location的url，这在
            // 一些登录授权取cookie时很重要
            if (locationHeader != null) {
                String redirectUrl = locationHeader.getValue();
                this.doRequest(redirectUrl, null, null, null);
            }
        } catch (HttpException e) {
            throw new Exception(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());

        } finally {
            if (rd != null) {
                try {
                    rd.close();
                } catch (IOException e) {
                    throw new Exception(e.getMessage());
                }
            }
            if (responseStream != null) {
                try {
                    responseStream.close();
                } catch (IOException e) {
                    throw new Exception(e.getMessage());

                }
            }
        }
        return responseContent + "*" + temp;
    }

    /**
     * 特殊请求数据,这样的请求往往会出现redirect本身而出现递归死循环重定向 所以单独写成一个请求方法
     * 比如现在请求的url为：http://localhost:8080/demo/index.jsp 返回代码为302
     * 头部信息中location值为:http://localhost:8083/demo/index.jsp
     * 这时httpclient认为进入递归死循环重定向，抛出CircularRedirectException异常
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String doSpecialRequest(String url, int count, String encoding) throws Exception {
        String str = null;
        InputStream responseStream = null;
        BufferedReader rd = null;
        GetMethod getRequest = new GetMethod(url);
        // 关闭httpclient自动重定向动能
        getRequest.setFollowRedirects(false);
        try {

            this.client.executeMethod(getRequest);
            Header header = getRequest.getResponseHeader("location");
            if (header != null) {
                // 请求重定向后的ＵＲＬ，count同时加1
                this.doSpecialRequest(header.getValue(), count + 1, encoding);
            }
            // 这里用count作为标志位，当count为0时才返回请求的ＵＲＬ文本,
            // 这样就可以忽略所有的递归重定向时返回文本流操作，提高性能
            if (count == 0) {
                getRequest = new GetMethod(url);
                getRequest.setFollowRedirects(false);
                this.client.executeMethod(getRequest);
                responseStream = getRequest.getResponseBodyAsStream();
                rd = new BufferedReader(new InputStreamReader(responseStream, encoding));
                String tempLine = rd.readLine();
                StringBuffer tempStr = new StringBuffer();
                String crlf = System.getProperty("line.separator");
                while (tempLine != null) {
                    tempStr.append(tempLine);
                    tempStr.append(crlf);
                    tempLine = rd.readLine();
                }
                str = tempStr.toString();
            }

        } catch (HttpException e) {
            throw new Exception(e.getMessage());
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        } finally {
            getRequest.releaseConnection();
            if (rd != null) {
                try {
                    rd.close();
                } catch (IOException e) {
                    throw new Exception(e.getMessage());
                }
            }
            if (responseStream != null) {
                try {
                    responseStream.close();
                } catch (IOException e) {
                    throw new Exception(e.getMessage());
                }
            }
        }
        return str;
    }
}

