package com.yangmiemie.score.spider.page;


import com.yangmiemie.score.dao.ISubjectDao;
import com.yangmiemie.score.dao.impl.SubjectDaoImpl;
import com.yangmiemie.score.entity.Subject;
import com.yangmiemie.score.entity.User;
import com.yangmiemie.score.service.ISubjectService;
import com.yangmiemie.score.service.impl.SubjectServiceImpl;
import com.yangmiemie.score.spider.link.Links;
import com.yangmiemie.score.spider.util.FileTool;
import com.yangmiemie.score.spider.util.URL;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Yang.
 * Email : vincent1094259423@yahoo.com
 * Date  : 2018-05-29 16:51
 * Description:
 */
public class HttpTool {
    private User user;
    private CookieStore cookies = new BasicCookieStore();
    private CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookies).build();


    public HttpTool() {
    }

    public HttpTool(User user) {
        this.user = user;
    }

    public Page sendRequestAndGetResponse(String url) throws IOException {
        HttpGet getMethod = new HttpGet(url);
        HttpResponse response = client.execute(getMethod);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            System.err.println("Method failed: " + response.getStatusLine());
        }
        HttpEntity entity = response.getEntity(); // 获取返回实体
        byte[] responseBody = EntityUtils.toByteArray(entity);
        String contentType = entity.getContentType().getValue();
        return new Page(responseBody, url, contentType); //封装成为页面
    }

    public void login(String validateCode) throws IOException {
        if (validateCode.length() != 4) {
            System.out.println("验证码格式有误！");
            return;
        }
        HttpPost loginPost = new HttpPost(URL.LOGIN.getUrl());
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("isRemember", user.getIsRemember()));
        formParams.add(new BasicNameValuePair("Role", user.getRole()));
        formParams.add(new BasicNameValuePair("UserName", user.getUserName()));
        formParams.add(new BasicNameValuePair("Password", user.getPassword()));
        formParams.add(new BasicNameValuePair("ValidateCode", validateCode));
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
        loginPost.setEntity(formEntity);
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

        //登录后访问成绩页面
        HttpGet scoreGet = new HttpGet(URL.SCORE.getUrl());
        response = client.execute(scoreGet);
        entity = response.getEntity();

        byte[] responseBody = EntityUtils.toByteArray(entity);
        String contentType = entity.getContentType().getValue();
        Page page = new Page(responseBody, URL.LOGIN.getUrl(), contentType); //封装成为页面
        //得到超链接
        Set<String> links = PageParserTool.getScoreLinks(page);
        for (String link : links) {
            Links.addUnvisitedUrlQueue(link);
            System.out.println("新增爬取路径: " + link);
        }
    }


    public List<Subject> getScore(String url) throws IOException {
        List<Subject> list = new LinkedList<>();
        Page page = sendRequestAndGetResponse(url);
        FileTool.saveToLocal(page);
        Elements es = PageParserTool.select(page, "form tr:not(:first-child)");
        if (!es.isEmpty()) {
            for (Element e : es) {
                Elements tds = e.select("td");
                Subject subject = new Subject();
                subject.setNum(tds.get(0).text());
                subject.setName(tds.get(1).text());
                subject.setType(tds.get(2).text());
                subject.setGpa(Double.valueOf(tds.get(3).text()));
                subject.setCredit(Double.valueOf(tds.get(4).text()));
                subject.setScore(Double.valueOf(tds.get(5).text()));
                subject.setSignUp(tds.get(6).text());
                subject.setAnnounce(tds.get(7).text());
                list.add(subject);
            }
        }
        return list;
    }
}
