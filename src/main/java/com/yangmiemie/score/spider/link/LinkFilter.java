package com.yangmiemie.score.spider.link;

/**
 * Created by Yang.
 * Email : vincent1094259423@yahoo.com
 * Date  : 2018-05-30 08:37
 * Description:
 */
public interface LinkFilter {
    boolean accept(String url);
}
