package com.yangmiemie.spider.main;

import com.yangmiemie.spider.page.Page;
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

/**
 * Created by Yang.
 * Email : vincent1094259423@yahoo.com
 * Date  : 2018-05-30 10:58
 * Description:
 */
public class Login {
    private String isRemember = "0";
    private String role = "student";
    private String userName = "1310321113";
    private String password = "yhh9441000";
    private AbstractHttpClient client = new DefaultHttpClient();

    public Page login(String validateCode) throws IOException {
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
        HttpResponse response = client.execute(loginPost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            entity.consumeContent();
        }

        Header[] headers = response.getHeaders("Set-Cookie");
        //访问首页获取cookie
        HttpGet httpGet = new HttpGet(URL.INDEX.getUrl());
        httpGet.setHeader("Referer", URL.LOGIN.getUrl());
        // 执行get请求
        response = client.execute(httpGet);
        entity = response.getEntity(); // 获取返回实体
        String content = "";
        if (entity != null) {
            // 转化为文本信息, 设置爬取网页的字符集，防止乱码
            content = EntityUtils.toString(entity, "UTF-8");
        }

        CookieStore cookie = client.getCookieStore();
        System.out.println(">> login response content: \n" + content);

        List<Cookie> cookieList = cookie.getCookies();
        System.out.println("cookie2222: " + cookieList);

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("statusCode: " + statusCode);
        if (statusCode != HttpStatus.SC_OK) {
            System.err.println("Method failed: " + response.getStatusLine());
        }

        entity = response.getEntity(); // 获取返回实体
        byte[] responseBody = EntityUtils.toByteArray(entity);
        String contentType = entity.getContentType().getValue();
        page = new Page(responseBody, URL.LOGIN.getUrl(), contentType); //封装成为页面
        return page;
    }
}
