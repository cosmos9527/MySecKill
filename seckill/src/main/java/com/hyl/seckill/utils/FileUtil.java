package com.hyl.seckill.utils;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/4/12
 * Time: 16:01
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class FileUtil {

    /**
     * 读取txt文件内容
     * @param filePath
     * @return
     */
    public static List<String> readTxt(String filePath){
        File file = new File(filePath);
        List<String> tokens = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        try{
            // 构造一个 BufferedReader 来读取文件
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while ((s = br.readLine()) != null){ // 使用readLine一次读取一行
                //stringBuilder.append(System.lineSeparator() + s);
                tokens.add(s.split(",")[1]);
            }
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return tokens;
    }

//    public static void main(String[] args) {
//        List<String> tokens = readTxt("D:\\tokens.txt");
//        System.out.println(JSON.toJSONString(tokens));
//    }
}
