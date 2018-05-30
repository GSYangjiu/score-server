package com.yangmiemie.spider.main;

import com.yangmiemie.spider.link.Links;
import com.yangmiemie.spider.page.Page;
import com.yangmiemie.spider.page.PageParserTool;
import com.yangmiemie.spider.util.URL;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.HeaderGroup;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Yang.
 * Email : vincent1094259423@yahoo.com
 * Date  : 2018-05-30 10:58
 * Description:
 */
public class Login {
    private String isRemember = "1";
    private String role = "Student";
    private String userName = "1310321113";
    private String password = "yhh9441000";
    private AbstractHttpClient client = new DefaultHttpClient();

    public CookieStore login(String validateCode, CookieStore cookies) throws IOException {
        Page page = null;
        if (validateCode.length() != 4) {
            System.out.println("验证码格式有误！");
            return null;
        }
        HttpPost loginPost = new HttpPost(URL.LOGIN.getUrl());
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("isRemember", isRemember));
        formParams.add(new BasicNameValuePair("Role", role));
        formParams.add(new BasicNameValuePair("UserName", userName));
        formParams.add(new BasicNameValuePair("Password", password));
        formParams.add(new BasicNameValuePair("ValidateCode", validateCode));
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
        loginPost.setEntity(formEntity);
        client.setCookieStore(cookies);
        HttpResponse response = client.execute(loginPost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            entity.consumeContent();
        }

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("statusCode: " + statusCode);
        if (statusCode != HttpStatus.SC_OK) {
            System.err.println("Method failed: " + response.getStatusLine());
        }

        HttpGet indexGet = new HttpGet(URL.SCORE.getUrl());
        response = client.execute(indexGet);
        entity = response.getEntity();

        byte[] responseBody = EntityUtils.toByteArray(entity);
        String contentType = entity.getContentType().getValue();
        page = new Page(responseBody, URL.LOGIN.getUrl(), contentType); //封装成为页面
        //得到超链接
        Set<String> links = PageParserTool.getScoreLinks(page);
        for (String link : links) {
            Links.addUnvisitedUrlQueue(link);
            System.out.println("新增爬取路径: " + link);
        }
        return client.getCookieStore();
    }
}
