package com.yx.chat.room.single;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * 聊天室客户端程序
 * Author:Sophie
 * Created: 2019/2/23
 */
public class client {
    public static void main(String[] args){
        try {
            //通过命令行获取端口号
            int port=4131;
            if(args.length>0) {
                try {
                    port = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    System.out.println("端口参数不正确，采用默认端口" + port);
                }
            }
            String host="127.0.0.1";
            if(args.length>1){
                host=args[1];
            }

            //准备Socket对象，连接到服务器端,表示连接哪个服务器的哪个进程
            //连接谁的计算机，就写那个计算机的IP地址
            Socket client = new Socket(host,port);
            System.out.println("连接到服务端，端口号："+client.getPort());

            //发送数据
            OutputStream clientoutput=client.getOutputStream();
            OutputStreamWriter writer=new OutputStreamWriter(clientoutput);
            writer.write("你好，我是客户端\n");
            writer.flush();

            PrintStream clientoutput1=new PrintStream(client.getOutputStream());
            clientoutput1.println("你好啊\n");

            //接收数据
            InputStream clientInput=client.getInputStream();
            Scanner scanner=new Scanner(clientInput);
            String Info= scanner.nextLine();
            System.out.println("来自服务端的数据："+Info);

            //关闭客户端
            clientInput.close();
            clientoutput1.close();
            client.close();
            System.out.println("客户端关闭");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
