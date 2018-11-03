package com.txp.app.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 模拟手机上报日志程序
 */
public class UploadUtil {

    /**
     * 上传日志
     */
    public static void upload(String json) throws Exception {
        try{
            //输入流
            InputStream in = ClassLoader.getSystemResourceAsStream("log.json");

            URL url = new URL("http://192.168.1.111:8080/app-web/coll/index");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置请求方式为post
            conn.setRequestMethod("POST");

            //时间头用来供server进行时钟校对的
            conn.setRequestProperty("clientTime",System.currentTimeMillis() + "");
            //允许上传数据
            conn.setDoOutput(true);
            //设置请求的头信息,设置内容类型
            conn.setRequestProperty("Content-Type", "application/json");


            //输出流
            OutputStream out = conn.getOutputStream();
            out.write(json.getBytes());
            out.flush();
            out.close();
            in.close();
            int code = conn.getResponseCode();
            System.out.println(code);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
