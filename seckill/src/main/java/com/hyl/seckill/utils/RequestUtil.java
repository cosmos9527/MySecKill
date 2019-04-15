package com.hyl.seckill.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * request请求工具类
 */
public class RequestUtil {

    /**
     * 打印request请求参数
     * @param request
     */
    public static void printParam(HttpServletRequest request){
        Map<String,String> map = new HashMap<>();

        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()){
            String pKey = (String) paramNames.nextElement();
            String[] pValues = request.getParameterValues(pKey);
            if(pValues.length == 1){
               map.put(pKey,pValues[0]);
            }
        }

        //打印map所有值
        Set<Map.Entry<String,String>> set = map.entrySet();
        System.out.println("********************************");
        for (Map.Entry entry:set) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        System.out.println("********************************");
    }
}
