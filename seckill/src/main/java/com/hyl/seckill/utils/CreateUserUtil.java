package com.hyl.seckill.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyl.seckill.entity.User;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 创建用户辅助类
 */
public class CreateUserUtil {

    public static void createUser(int count) throws Exception{
        List<User> users = new ArrayList<>();
        //生成用户
        for (int i = 10000;i <= 10000+count;i++){
            User user = new User();
            user.setPhone("131000" + i);
            user.setLoginCount(1);
            user.setSalt("1a2b3c4d");
            user.setUserName("user" + i);
            user.setRegisterDate(new Date());
            user.setPassword(MD5Utils.inputPass2DbPass("123456",user.getSalt()));
            users.add(user);
        }

        //将用户插入到数据库
//        System.out.println("Insert User into DB !");
//        Connection connection = DBUtil.getConnection();
//        String sql = "insert into user(login_count, user_name, register_date, salt, password,phone)values(?,?,?,?,?,?)";
//        PreparedStatement ps = connection.prepareStatement(sql);
//        for (int i = 0;i < users.size();i++){
//            ps.setInt(1,users.get(i).getLoginCount());
//            ps.setString(2,users.get(i).getUserName());
//            ps.setTimestamp(3,new Timestamp(users.get(i).getRegisterDate().getTime()));
//            ps.setString(4,users.get(i).getSalt());
//            ps.setString(5,users.get(i).getPassword());
//            ps.setString(6,users.get(i).getPhone());
//            ps.addBatch();
//        }
//
//        ps.executeBatch();
//        ps.close();
//        connection.close();
//
//        System.out.println("Insert End!");

        // 登录 生成 token
        String requestUrl = "http://localhost:8888/user/login";
        File file = new File("D:/tokens.txt");
        if (file.exists()) {
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        file.createNewFile();
        raf.seek(0);
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            System.out.println("登录用户:" + user.getPhone());
            URL url = new URL(requestUrl);
            HttpURLConnection co = (HttpURLConnection) url.openConnection();
            co.setRequestMethod("POST");
            co.setDoOutput(true);
            OutputStream out = co.getOutputStream();
            String params = "mobile=" + user.getPhone() + "&password=" + MD5Utils.inputPass2FormPass("123456");
            out.write(params.getBytes());
            out.flush();
            InputStream inputStream = co.getInputStream();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte buff[] = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buff)) >= 0) {
                bout.write(buff, 0, len);
            }
            inputStream.close();
            bout.close();
            String response = new String(bout.toByteArray());
            JSONObject jo = JSON.parseObject(response);
            System.out.println("response:" + JSON.toJSONString(response));
            String token = jo.getString("data");
            System.out.println("create token : " + token);

            String row = user.getPhone() + "," + token;
            raf.seek(raf.length());
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
            System.out.println("write to file : " + user.getId());
        }
        raf.close();

    }



    public static void main(String[] args) throws Exception {
        createUser(10000);
    }
}
