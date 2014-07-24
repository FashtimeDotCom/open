package com.jscn.open.admin.filter;

import java.io.Serializable;

import com.jscn.commons.core.utils.EncryptUtil;

public class User implements Serializable{
    private static final long serialVersionUID = -2617010534960679994L;
    
    public static final String SESSION_KEY = "USER";
    private static String userName = "admin";
    private static String password = "387991d3ff4dfd0e5b545a151582e340";
    private String ip;
    
    public static boolean login(String userName,String password){
        return User.userName.equals(userName) && validPassword(password);
    }
    
    public String getUserName() {
        return userName;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    private static boolean validPassword(String password){
        if(User.password.equals(EncryptUtil.md5Hex(password+"SDFHHRDEWtwetewrwf232322","utf-8"))){
            return true;
        }else{
            return false;
        }
    }
    
//    public static void main(String args[]){
//        System.out.println(EncryptUtil.md5Hex("adminSDFHHRDEWtwetewrwf232322","utf-8"));
//    }
    
}
