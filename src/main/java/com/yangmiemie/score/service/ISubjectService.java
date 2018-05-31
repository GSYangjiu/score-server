package com.yangmiemie.score.service;

import com.yangmiemie.score.entity.Subject;
import com.yangmiemie.score.entity.User;

/**
 * Created by Yang.
 * Email : vincent1094259423@yahoo.com
 * Date  : 2018-05-31 10:01
 * Description:
 */
public interface ISubjectService {

    /**
     * 从爬取的字符串中获取成绩
     *
     * @param entity
     */
    void add(Subject entity);

    void crawling(User user);
}
