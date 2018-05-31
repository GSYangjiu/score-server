package com.yangmiemie.score.dao.impl;

import com.yangmiemie.score.dao.ISubjectDao;
import com.yangmiemie.score.entity.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Yang.
 * Email : vincent1094259423@yahoo.com
 * Date  : 2018-05-31 11:18
 * Description:
 */
@Repository("subjectDao")
public class SubjectDaoImpl implements ISubjectDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(SubjectDaoImpl.class);

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Override
    public int insert(Subject entity) {
        try {
            mongoTemplate.insert(entity);
            return 1;
        } catch (Exception e) {
            LOGGER.error("添加失败:", e);
            return -1;
        }
    }
}

