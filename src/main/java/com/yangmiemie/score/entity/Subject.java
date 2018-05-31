package com.yangmiemie.score.entity;

/**
 * Created by Yang.
 * Email : vincent1094259423@yahoo.com
 * Date  : 2018-05-31 09:47
 * Description:
 */
public class Subject {
    private Long studentId;
    private String num;         //任务编号
    private String name;
    private String type;
    private Double gpa;
    private Double credit;
    private Double score;
    private String signUp;      //补考报名
    private String announce;    //公布

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getSignUp() {
        return signUp;
    }

    public void setSignUp(String signUp) {
        this.signUp = signUp;
    }

    public String getAnnounce() {
        return announce;
    }

    public void setAnnounce(String announce) {
        this.announce = announce;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "studentId=" + studentId +
                ", num='" + num + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", gpa=" + gpa +
                ", credit=" + credit +
                ", score=" + score +
                ", signUp='" + signUp + '\'' +
                ", announce='" + announce + '\'' +
                '}';
    }
}
