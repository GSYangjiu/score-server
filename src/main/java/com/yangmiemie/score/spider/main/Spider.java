package com.yangmiemie.score.spider.main;

import com.yangmiemie.score.entity.Subject;
import com.yangmiemie.score.entity.User;
import com.yangmiemie.score.spider.link.Links;
import com.yangmiemie.score.spider.page.HttpTool;
import com.yangmiemie.score.spider.page.Page;
import com.yangmiemie.score.spider.util.FileTool;
import com.yangmiemie.score.spider.util.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Yang.
 * Email : vincent1094259423@yahoo.com
 * Date  : 2018-05-30 09:02
 * Description:
 */
public class Spider {
    private final static Logger LOGGER = LoggerFactory.getLogger(Spider.class);

    public static void main(String[] args) {
        Spider spider = new Spider();
        User user = new User("1", "Student", "1310321113", "");
        spider.crawling(user);
    }

    public List<Subject> crawling(User user) {
        HttpTool httpTool = new HttpTool(user);
        List<Subject> subjects = new LinkedList<>();
        try {
            //获取验证码
            Page pageLogin = httpTool.sendRequestAndGetResponse(URL.VALIDATE_CODE.getUrl());
            FileTool.saveToLocal(pageLogin);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            LOGGER.info("请输入验证码：");
            //登陆，并将待查询成绩链接加入访问池
            httpTool.login(br.readLine());
            //查询成绩
            while (!Links.unVisitedUrlQueueIsEmpty()) {
                //先从待访问的序列中取出第一个；
                String visitUrl = (String) Links.removeHeadOfUnVisitedUrlQueue();
                if (visitUrl == null) {
                    continue;
                }
                //根据URL得到List<Subject>;
                subjects.addAll(httpTool.getScore(visitUrl));
                Thread.sleep(1000);
                //将已经访问过的链接放入已访问的链接中；
                Links.addVisitedUrlSet(visitUrl);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return subjects;
    }
}

