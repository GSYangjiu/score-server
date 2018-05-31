package com.yangmiemie.score.controller;

import com.yangmiemie.score.common.utils.Message;
import com.yangmiemie.score.entity.User;
import com.yangmiemie.score.service.ISubjectService;
import com.yangmiemie.score.spider.page.HttpTool;
import com.yangmiemie.score.spider.page.Page;
import com.yangmiemie.score.spider.util.FileTool;
import com.yangmiemie.score.spider.util.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Yang.
 * Email : vincent1094259423@yahoo.com
 * Date  : 2018-05-31 11:52
 * Description:
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    ISubjectService subjectService;

    @GetMapping("/login")
    public void login(HttpServletRequest request) {
        User user = new User("1", "Student", "1310321113", "yhh9441000");
        subjectService.crawling(user);
    }

    @GetMapping("/getValidateCode")
    public Message getValidateCode(HttpServletRequest request) {
        HttpTool tool = new HttpTool();
        Page pageLogin = null;
        try {
            pageLogin = tool.sendRequestAndGetResponse(URL.VALIDATE_CODE.getUrl());
            FileTool.saveToLocal(pageLogin);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Message();
    }
}
