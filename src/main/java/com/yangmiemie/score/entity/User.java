package com.yangmiemie.score.entity;

/**
 * Created by Yang.
 * Email : vincent1094259423@yahoo.com
 * Date  : 2018-05-31 09:02
 * Description:
 */
public class User {
    private String isRemember;
    private String role;
    private String userName;
    private String password;

    public User() {

    }

    public User(String isRemember, String role, String userName, String password) {
        this.isRemember = isRemember;
        this.role = role;
        this.userName = userName;
        this.password = password;
    }

    public String getIsRemember() {
        return isRemember;
    }

    public void setIsRemember(String isRemember) {
        this.isRemember = isRemember;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "isRemember='" + isRemember + '\'' +
                ", role='" + role + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
