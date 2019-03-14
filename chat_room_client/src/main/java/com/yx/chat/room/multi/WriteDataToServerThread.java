package com.yx.chat.room.multi;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Author:Sophie
 * Created: 2019/3/11
 */
public class WriteDataToServerThread extends Thread {
    private final Socket client;

    public WriteDataToServerThread(Socket client) {
        this.client = client;
    }

    public void run(){
        try {
            OutputStream outputStream=client.getOutputStream();
            OutputStreamWriter writer=new OutputStreamWriter(outputStream);
            Scanner scanner=new Scanner(System.in);
            while(true){
                System.out.println("请输入消息：");
                //给服务器发数据
                String message=scanner.nextLine();
                writer.write(message+"\n");
                writer.flush();

                if (message.endsWith("bye")){
                    client.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
