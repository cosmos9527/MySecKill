package com.hyl.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {

    private static final String SALT = "1a2b3c4d";

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    public static String inputPass2FormPass(String inputPass){
        String src = "" + SALT.charAt(0) + SALT.charAt(2)+ inputPass + SALT.charAt(5)+ SALT.charAt(4);
        return md5(src);
    }

    public static String formPass2DbPass(String formPass, String salt){
        String src = "" + salt.charAt(0) + salt.charAt(2)+ formPass + salt.charAt(5)+ salt.charAt(4);
        return md5(src);
    }

    public static String inputPass2DbPass(String inputPass, String salt){
        String formPass = inputPass2FormPass(inputPass);
        String dbPass = formPass2DbPass(formPass, salt);
        return dbPass;
    }

//    public static void main(String[] args) {
//        String inputPass = "123456";
//        String salt = "mysalt";
//        String formPass = inputPass2FormPass(inputPass);
//        String dbPass1 = formPass2DbPass(formPass, SALT);
//        String dbPass2 = inputPass2DbPass(inputPass, salt);
//        System.out.println(formPass);
//        System.out.println(dbPass1);
//        System.out.println(dbPass2);
//    }
}
