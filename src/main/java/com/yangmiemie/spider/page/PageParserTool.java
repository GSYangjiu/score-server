package com.yangmiemie.spider.page;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.tomcat.util.buf.Utf8Encoder;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Yang.
 * Email : vincent1094259423@yahoo.com
 * Date  : 2018-05-29 17:11
 * Description:
 */
public class PageParserTool {
    /* 通过选择器来选取页面的 */
    public static Elements select(Page page, String cssSelector) {
        return page.getDoc().select(cssSelector);
    }

    /*
     *  通过css选择器来得到指定元素;
     *
     *  */
    public static Element select(Page page, String cssSelector, int index) {
        Elements eles = select(page, cssSelector);
        int realIndex = index;
        if (index < 0) {
            realIndex = eles.size() + index;
        }
        return eles.get(realIndex);
    }


    /**
     * 获取满足选择器的元素中的链接 选择器cssSelector必须定位到具体的超链接
     * 例如我们想抽取id为content的div中的所有超链接，这里
     * 就要将cssSelector定义为div[id=content] a
     * 放入set 中 防止重复；
     *
     * @param cssSelector
     * @return
     */
    public static Set<String> getLinks(Page page, String cssSelector) {
        Set<String> links = new HashSet<String>();
        Elements es = select(page, cssSelector);
        Iterator iterator = es.iterator();
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            if (element.hasAttr("href")) {
                links.add(element.attr("abs:href"));
            } else if (element.hasAttr("src")) {
                links.add(element.attr("abs:src"));
            }
        }
        return links;
    }

    /**
     * @param page
     * @return
     */
    public static Set<String> getScoreLinks(Page page) {
        Set<String> links = new HashSet<>();
        Elements es = select(page, "option");
        Iterator iterator = es.iterator();
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            if (element.hasAttr("value")) {
                String str = element.attr("value");
                String year = str.substring(0, 4);
                String number = str.substring(str.length() - 1);
                switch (number) {
                    case "1":
                        number = "一";
                        break;
                    case "2":
                        number = "二";
                        break;
                    case "3":
                        number = "三";
                        break;
                    case "4":
                        number = "四";
                        break;
                }

                String link = null;
                try {
                    link = "http://run.hbut.edu.cn/StuGrade/Index?SemesterName=" + str + "&SemesterNameStr=" + URLEncoder.encode(year + "学年 第" + number + "学期", "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                links.add(link);
            }
        }
        return links;
    }

    /**
     * 获取网页中满足指定css选择器的所有元素的指定属性的集合
     * 例如通过getAttrs("img[src]","abs:src")可获取网页中所有图片的链接
     *
     * @param cssSelector
     * @param attrName
     * @return
     */
    public static ArrayList<String> getAttrs(Page page, String cssSelector, String attrName) {
        ArrayList<String> result = new ArrayList<String>();
        Elements eles = select(page, cssSelector);
        for (Element ele : eles) {
            if (ele.hasAttr(attrName)) {
                result.add(ele.attr(attrName));
            }
        }
        return result;
    }
}
