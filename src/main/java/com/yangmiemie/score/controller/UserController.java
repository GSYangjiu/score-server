package com.yangmiemie.score.controller;

import com.yangmiemie.score.entity.User;
import com.yangmiemie.score.service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void login() {
        User user = new User("1", "Student", "1310321113", "yhh9441000");
        subjectService.crawling(user);
    }
}
