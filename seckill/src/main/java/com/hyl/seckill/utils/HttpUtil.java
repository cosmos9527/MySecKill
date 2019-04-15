package com.hyl.seckill.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/4/12
 * Time: 15:10
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class HttpUtil {

    /**
     * 向指定URL发送GET请求
     * @param url 请求URL
     * @param param 请求参数 ： name1=value1&name2=value2
     * @return 响应结果
     */
    public static String sendGet(String url,String param){

        String result = "";

        BufferedReader in = null;
        try{
            String requestUrl = url + "?" + param;
            URL realUrl = new URL(requestUrl);
            //打开和URL之间的连接
            URLConnection urlConn = realUrl.openConnection();
            //设置通用请求属性
            urlConn.setRequestProperty("accept","*/*");
            urlConn.setRequestProperty("connection","Keep-Alive");
            urlConn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //建立实际的链接
            urlConn.connect();
            //获取响应投字段
            Map<String, List<String>> map = urlConn.getHeaderFields();
            //遍历所有响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + " --> " + map.get(key));
            }
            in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line ;
            while ((line = in.readLine()) != null){
                result +=  line;
            }

        }catch (Exception e){
            System.out.println("发送GET请求发生错误!");
            e.printStackTrace();
        }
        //关闭流
        finally {
            try{
                in.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定的URL发送POST请求
     * @param url 请求url
     * @param param 请求参数 ： name1=value1&name2=value2
     * @return 响应结果
     */
    public static String sendPost(String url,String param){
        PrintWriter pw = null;
        BufferedReader in = null;
        String result = "";
        try{
            URL realUrl = new URL(url);
            //打开和URL之间的连接
            URLConnection urlConn = realUrl.openConnection();
            //设置通用的请求属性
            urlConn.setRequestProperty("accept","*/*");
            urlConn.setRequestProperty("connection","Keep-Alive");
            urlConn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //发送POST请求必须设置的两个属性
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            // 获取URLConnection对象对应的输出流
            pw = new PrintWriter(urlConn.getOutputStream());
            // 发送请求参数
            pw.print(param);
            // flush输出流的缓冲
            pw.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            String line;
            while ((line = in.readLine()) != null){
                result += line;
            }
        }catch (Exception e){
            System.out.println("发送POST请求发生异常!" + e);
            e.printStackTrace();
        }finally {
            try{
                if(pw != null){
                    pw.close();
                }
                if(in != null){
                    in.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

//    public static void main(String[] args) {
//        String url = "http://localhost:8888/seckill/seckill";
//        String params = "goodsId=1&token=64038afc7805486e9015b91755c1a8a7";
//        String response = HttpUtil.sendGet(url,params);
//        System.out.println("response : " + response);
//    }

}
