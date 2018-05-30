package com.yangmiemie.spider.main;

import com.yangmiemie.spider.page.Page;
import com.yangmiemie.spider.page.PageParserTool;
import com.yangmiemie.spider.page.RequestAndResponseTool;
import com.yangmiemie.spider.util.FileTool;
import com.yangmiemie.spider.util.URL;
import org.jsoup.select.Elements;

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
        FileTool.saveToLocal(RequestAndResponseTool.sendRequestAndGetResponse(index));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("请输入验证码：");
        try {
            //使用验证码登陆
            String validateCode = br.readLine();
            Login login = new Login();
            Page page = login.login(validateCode);
            //对page进行处理： 访问DOM的某个标签
            Elements es = PageParserTool.select(page,"a");
            if(!es.isEmpty()){
                System.out.println("下面将打印所有a标签： ");
                System.out.println(es);
            }
            FileTool.saveToLocal(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

