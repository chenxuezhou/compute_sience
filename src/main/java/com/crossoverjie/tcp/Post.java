package com.crossoverjie.tcp;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Post {

    static String referer = "";

    static String coockie = "";


    public static void main(String[] args) {
        String param = "";
        String url = "";
        sendPost(url, param);

    }

    public static String sendPost(String curl, String param) {
        String result = "";// 返回的结果
        BufferedReader in = null;// 读取响应输入流
        try {
            //创建连接
            URL url = new URL(curl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();

            connection.setDoOutput(true); //是否打开outputStream 相对于程序，即我们向远程服务器写入数据，默认为false，不打开
            connection.setDoInput(true);  //输入流，获取到返回的响应内容， 默认为true，所以get请求时可以不设置这个连接信息
            connection.setRequestMethod("POST"); //发送请求的方式
            connection.setUseCaches(false); //不使用缓存
            connection.setInstanceFollowRedirects(true); //重定向，一般浏览器才需要
            connection.setRequestProperty("Content-Type",
                    "text/plain;charset=UTF-8"); //设置服务器解析数据的方式
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible;MSIE 7.0; Windows NT 5.1; Maxthon;)");
//            connection.setRequestProperty("Accept-Encoding", "gzip");
            connection.setRequestProperty("referer", referer);
            connection.setRequestProperty("cookie", coockie);

            connection.connect();

            //POST请求
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));//服务器接受和返回都是UTF-8格式
            out.write(param);
            out.flush();
            out.close();

            //读取响应
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));//
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Http请求方法内部问题");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
