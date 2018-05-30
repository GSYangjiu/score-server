package com.yangmiemie.spider.util;

/**
 * Created by Yang.
 * Email : vincent1094259423@yahoo.com
 * Date  : 2018-05-30 10:51
 * Description:
 */
public enum URL {
    LOGIN("http://run.hbut.edu.cn/Account/LogOn"),
    VALIDATE_CODE("http://run.hbut.edu.cn/Account/GetValidateCode"),
    INDEX("http://run.hbut.edu.cn"),
    SCORE("http://run.hbut.edu.cn/StuGrade/Index");


    String url;

    URL(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
