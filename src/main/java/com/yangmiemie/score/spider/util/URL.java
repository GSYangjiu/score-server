package com.yangmiemie.score.spider.util;

/**
 * Created by Yang.
 * Email : vincent1094259423@yahoo.com
 * Date  : 2018-05-30 10:51
 * Description:
 */
public enum URL {
    INDEX("http://run.hbut.edu.cn"),
    LOGIN("/Account/LogOn"),
    VALIDATE_CODE("/Account/GetValidateCode"),
    SCORE("/StuGrade/Index");


    String url;

    URL(String url) {
        this.url = url;
    }

    public String getUrl() {
        if (this.equals(INDEX))
            return url;
        return INDEX.url + url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
