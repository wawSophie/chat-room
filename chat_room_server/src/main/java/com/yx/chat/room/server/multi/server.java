package com.yx.chat.room.server.multi;

/**
 * Author:Sophie
 * Created: 2019/2/24
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class server {
    public static void main(String[] args){

        int port=4131;
        if (args.length>0){
            try {
                port = Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                System.out.println("输出端口不正确，采用默认端口"+port);
            }
        }
        //1、建立一个固定数量的线程池
        ExecutorService executorService=Executors.newFixedThreadPool(10);

        //2、创建服务端Socket，端口号为4131,作用：接收客户端
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("等待客户端连接...");
            while(true) {
                //3、端口接收客户端
                Socket client = serverSocket.accept();
                //4、服务端显示已连接客户端的信息
                System.out.println("有新的客户端连接，端口号为：" + client.getPort());
                //执行客户端发出的任务信息
                executorService.submit(new ExecuteClient(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

