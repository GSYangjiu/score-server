package com.yangmiemie.spider.page;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Yang.
 * Email : vincent1094259423@yahoo.com
 * Date  : 2018-05-29 16:51
 * Description:
 */
public class RequestAndResponseTool {
    public static Page sendRequestAndGetResponse(String url) {
        Page page = null;
        AbstractHttpClient client = new DefaultHttpClient();
        HttpGet getMethod = new HttpGet(url);
        try {
            HttpResponse response = client.execute(getMethod);
            //获取cookies
            CookieStore cookie = client.getCookieStore();
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + response.getStatusLine());
            }
            HttpEntity entity = response.getEntity(); // 获取返回实体
            byte[] responseBody = EntityUtils.toByteArray(entity);
            String contentType = entity.getContentType().getValue();
            page = new Page(responseBody, url, contentType, cookie); //封装成为页面
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            getMethod.releaseConnection();
        }
        return page;
    }

    public static void getScore(String url, CookieStore cookies) {
        Page page = null;
        AbstractHttpClient client = new DefaultHttpClient();
        client.setCookieStore(cookies);
        HttpGet getMethod = new HttpGet(url);
        try {
            HttpResponse response = client.execute(getMethod);
            //获取cookies
            CookieStore cookie = client.getCookieStore();
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + response.getStatusLine());
            }
            HttpEntity entity = response.getEntity(); // 获取返回实体
            byte[] responseBody = EntityUtils.toByteArray(entity);
            String contentType = entity.getContentType().getValue();
            page = new Page(responseBody, url, contentType, cookie); //封装成为页面
            Elements es = PageParserTool.select(page, "th");
            if (!es.isEmpty()) {
                System.out.println("打印所有列： ");
                System.out.println(es);
                //数据持久化
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            getMethod.releaseConnection();
        }
    }
}
