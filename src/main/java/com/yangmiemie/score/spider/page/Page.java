package com.yangmiemie.score.spider.page;

import com.yangmiemie.score.spider.util.CharsetDetector;
import org.apache.http.client.CookieStore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.UnsupportedEncodingException;

/**
 * Created by Yang.
 * Email : vincent1094259423@yahoo.com
 * Date  : 2018-05-29 16:52
 * Description:
 */
public class Page {
    private byte[] content;     //response
    private String html;        //网页源码字符串
    private Document doc;       //网页DOM文档
    private String charset;     //字符编码
    private String url;         //url路径
    private String contentType; //内容类型
    private CookieStore cookies; //cookies

    public Page(byte[] content, String url, String contentType) {
        this.content = content;
        this.url = url;
        this.contentType = contentType;
    }

    public Page(byte[] content, String url, String contentType, CookieStore cookies) {
        this.content = content;
        this.url = url;
        this.contentType = contentType;
        this.cookies = cookies;
    }

    public String getCharset() {
        return charset;
    }

    public String getUrl() {
        return url;
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getContent() {
        return content;
    }

    /**
     * 返回网页的源码字符串
     *
     * @return 网页的源码字符串
     */
    public String getHtml() {
        if (html != null) {
            return html;
        }
        if (content == null) {
            return null;
        }
        if (charset == null) {
            charset = CharsetDetector.guessEncoding(content); // 根据内容来猜测 字符编码
        }
        try {
            this.html = new String(content, charset);
            return html;
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public CookieStore getCookies() {
        return cookies;
    }

    public void setCookies(CookieStore cookies) {
        this.cookies = cookies;
    }

    /*
     *  得到文档
     * */
    public Document getDoc() {
        if (doc != null) {
            return doc;
        }
        try {
            this.doc = Jsoup.parse(getHtml(), url);
            return doc;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
