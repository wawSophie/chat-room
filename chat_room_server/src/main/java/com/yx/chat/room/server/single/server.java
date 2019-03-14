package com.yx.chat.room.server.single;

/**
 * 聊天室服务端程序
 * Author:Sophie
 * Created: 2019/2/24
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

//服务端一般不用关
public class server {
    public static void main(String[] args){

        try {
            //通过命令行获取服务器端口
            int port=4131;
            if(args.length>0){
                try{
                    port=Integer.parseInt(args[0]);
                }catch (NumberFormatException e){
                    System.out.println("端口参数不正确，采用默认端口"+port);
                }
            }
            //端口号：0-65535
            //创建服务器，等待连接
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务器信息："+serverSocket.getLocalSocketAddress());
            System.out.println("等待客户端连接...");

            //accept,监听客户端连接，这个方法是阻塞的，直到有客户端连接，返回的就是Socket
            //while(true){

                Socket client=serverSocket.accept();
                System.out.println("有新的客户端接入，端口号为："+client.getLocalSocketAddress());

                //Java I/O
                //接收数据
                InputStream clientInput=client.getInputStream();
                Scanner scanner=new Scanner(clientInput);
                String Info= scanner.nextLine();
                System.out.println("来自客户端的数据："+Info);

                //发送数据,由于接收数据使用nextline()，所以在发完数据之后必须换行，才能顺利读数据
                OutputStream clientoutput=client.getOutputStream();
                OutputStreamWriter writer=new OutputStreamWriter(clientoutput);
                writer.write("你好，我是服务端\n");
                writer.flush();
            //}

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
