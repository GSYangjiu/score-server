package com.yangmiemie.score.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yang.
 * Email : vincent1094259423@yahoo.com
 * Date  : 2018-04-18 14:38
 * Description:
 */
public class Message {
    private int status;
    private String error;
    private String info;
    private Map<String, Object> map;

    public Message() {
        this.status = MessageType.M10000.getStatus();
        this.error = MessageType.M10000.getMsg();
        this.map = new HashMap<>();
    }

    public Message(MessageType type) {
        this.status = type.getStatus();
        this.error = type.getMsg();
    }

    public Message(int status, String error) {
        this.status = status;
        this.error = error;
    }

    public Message(int status, String error, Map<String, Object> map) {
        this(status, error);
        this.map = map;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "Message{" +
                "status=" + status +
                ", error='" + error + '\'' +
                ", info='" + info + '\'' +
                ", map=" + map +
                '}';
    }
}
