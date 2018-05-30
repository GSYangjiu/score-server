package com.yangmiemie.spider.main;

import com.yangmiemie.spider.link.Links;
import com.yangmiemie.spider.page.Page;
import com.yangmiemie.spider.page.RequestAndResponseTool;
import com.yangmiemie.spider.util.FileTool;
import com.yangmiemie.spider.util.URL;
import org.apache.http.client.CookieStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Yang.
 * Email : vincent1094259423@yahoo.com
 * Date  : 2018-05-30 09:02
 * Description:
 */
public class Spider {
    public static void main(String[] args) {
        Spider spider = new Spider();
        spider.crawling(URL.VALIDATE_CODE.getUrl());
    }

    public void crawling(String index) {
        //获取验证码
        Page pageLogin = RequestAndResponseTool.sendRequestAndGetResponse(index);
        FileTool.saveToLocal(pageLogin);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("请输入验证码：");
        try {
            //使用验证码登陆
            String validateCode = br.readLine();
            Login login = new Login();
            CookieStore cookies = login.login(validateCode, pageLogin.getCookies());
            getScore(cookies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getScore(CookieStore cookies) {
        while (!Links.unVisitedUrlQueueIsEmpty()) {

            //先从待访问的序列中取出第一个；
            String visitUrl = (String) Links.removeHeadOfUnVisitedUrlQueue();
            if (visitUrl == null) {
                continue;
            }
            //根据URL得到page;
            RequestAndResponseTool.getScore(visitUrl,cookies);
            //将已经访问过的链接放入已访问的链接中；
            Links.addVisitedUrlSet(visitUrl);
        }
    }
}

