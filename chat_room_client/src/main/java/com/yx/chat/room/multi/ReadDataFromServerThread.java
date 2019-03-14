package com.yx.chat.room.multi;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Author:Sophie
 * Created: 2019/3/11
 */
public class ReadDataFromServerThread extends Thread{
   private final Socket client;
   public ReadDataFromServerThread(Socket client){
       this.client = client;
   }
   public void run(){
       try {
           InputStream clientInput=client.getInputStream();
           Scanner scanner=new Scanner(clientInput);
           while(true){
               String message=scanner.next();
               System.out.println("来自服务器的消息："+message);
               if (message.equals("bye")){
                   break;
               }
           }
       } catch (IOException e) {
           e.printStackTrace();
       }

   }
}
