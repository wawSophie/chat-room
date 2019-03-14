package com.yx.chat.room.multi;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Author:Sophie
 * Created: 2019/2/24
 */
public class client0 {
    public static void main(String[] args){
        int port=4131;
        if (args.length>0){
            try {
                port=Integer.parseInt(args[1]);
            }catch (NumberFormatException e){
                System.out.println("输出端口不正确，采用默认端口"+port);
            }
        }
        String host="127.0.0.1";
        if (args.length>1){
         host=args[1];
        }

        //1、客户端建立对服务端连接
        try {
            final Socket socket= new Socket(host,port);
            //2、连接到服务端，并显示服务端地址
            System.out.println("连接到服务端，地址为："+socket.getRemoteSocketAddress());
            new WriteDataToServerThread(socket).start();
            new ReadDataFromServerThread(socket).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
