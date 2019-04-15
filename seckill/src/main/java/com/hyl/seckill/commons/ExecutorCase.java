package com.hyl.seckill.commons;

import com.hyl.seckill.task.Task;
import com.hyl.seckill.utils.FileUtil;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/4/12
 * Time: 16:22
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class ExecutorCase {

    public static ExecutorService service = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {

        // 读取用户token
        List<String> tokens = FileUtil.readTxt("D:\\tokens.txt");

        // 遍历tokens
        for (int i = 0;i < tokens.size();i++){
            Task task = null;
            if(i % 2 == 0){
                task = new Task(1L,tokens.get(i));
            }else {
                task = new Task(2L,tokens.get(i));
            }
            service.execute(task);
        }
    }
}
