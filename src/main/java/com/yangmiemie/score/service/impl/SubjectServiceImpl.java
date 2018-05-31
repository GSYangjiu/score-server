package com.yangmiemie.score.service.impl;

import com.yangmiemie.score.dao.ISubjectDao;
import com.yangmiemie.score.entity.Subject;
import com.yangmiemie.score.entity.User;
import com.yangmiemie.score.service.ISubjectService;
import com.yangmiemie.score.spider.main.Spider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Yang.
 * Email : vincent1094259423@yahoo.com
 * Date  : 2018-05-31 10:02
 * Description:
 */
@Service("subjectService")
public class SubjectServiceImpl implements ISubjectService {

    @Autowired
    private ISubjectDao subjectDao;

    @Override
    public void add(Subject entity) {
        subjectDao.insert(entity);
    }

    @Override
    public void crawling(User user) {
        Spider spider = new Spider();
        List<Subject> subjects = spider.crawling(user);
        subjects.forEach(s -> subjectDao.insert(s));
    }
}
