package com.yx.chat.room.server.multi;

/**
 * 服务端处理客户端连接的任务
 * 1、注册
 * 2、私聊
 * 3、群聊
 * 4、退出
 * 5、显示当前在线任务
 * 6、统计任务活跃度
 * Author:Sophie
 * Created: 2019/2/24
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class ExecuteClient implements Runnable{
    /*
     *ConcurrentHashMap是在JDK1.5上添加的并发收集类，用于替代基于哈希的同步映射实现，它是线程安全的
     * 定义clientMap来存储用户信息
     */
    //static:实现成员Map共享
    private static final Map<String,Socket> ONLINE_USER_MAP =new ConcurrentHashMap<String, Socket>();
    //属性：用户
    private final Socket client;
    //构造方法
    public ExecuteClient(Socket client){
        this.client =client;
    }
    public void run() {
        try {
            //接收客户端的数据
            InputStream clientInput=this.client.getInputStream();
            Scanner scanner=new Scanner(clientInput);
            while (true){
            String line=scanner.nextLine();
            //TODO
            /*
            * 1、注册：userName:<name>
            * 2、私聊：private：<name>:<message>
            * 3、群聊：group：<message>
            * 4、退出：bye
             */
            if (line.startsWith("userName")){
                String userName=line.split("\\:")[1];
                this.register(userName,client);
            }

            if (line.startsWith("private")){
                String[] Info=line.split("\\:");
                String userName=Info[1];
                String message=Info[2];
                this.privateChat(userName,message);
            }
            if (line.startsWith("group")){
                String message=line.split("\\:")[1];
                this.groupChat(message);
            }
            if (line.equals("bye")){
                this.bye();
            }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void bye() {
        String currentUserName=this.currentClient();
        System.out.println("用户："+currentUserName+"下线");
        Socket socket=ONLINE_USER_MAP.get(currentUserName);
        this.sendMesssage(socket,"bye");
        ONLINE_USER_MAP.remove(currentUserName);
        printOnlineUser();
    }

    private void groupChat(String message) {
        for(Socket socket:ONLINE_USER_MAP.values()){
            if (socket.equals(this.currentClient())){
                continue;
            }
            this.sendMesssage(socket,this.currentClient()+"说:"+message);
        }
    }

    private void privateChat(String userName, String message) {
        String currentUserName=this.currentClient();
        Socket target=ONLINE_USER_MAP.get(userName);
        if (target!=null){
            sendMesssage(target,currentUserName+"对你说："+message);
        }
    }
    private String currentClient(){
        String currentUserName="";
        for (Map.Entry<String,Socket>entry:ONLINE_USER_MAP.entrySet()){
            if (this.client.equals(entry.getValue())){
                currentUserName=entry.getKey();
                break;
            }
        }
        return currentUserName;
    }
    private void register(String userName, Socket client) {
        System.out.println(userName+"加入到聊天室"+client.getRemoteSocketAddress());
        ONLINE_USER_MAP.put(userName,client);
        printOnlineUser();//注册一次显示一次当前在线人数
        sendMesssage(this.client,userName+"注册成功！");
    }

    private void sendMesssage(Socket socket,String Message){
        try {
            OutputStream clientOutput=socket.getOutputStream();
            OutputStreamWriter writer=new OutputStreamWriter(clientOutput);
            writer.write(Message+"\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void printOnlineUser(){
        System.out.println("当前在线人数："+ONLINE_USER_MAP.size()+"用户名如下列表：");
        for (Map.Entry<String,Socket>entry:ONLINE_USER_MAP.entrySet()){
            System.out.println(entry.getKey());
        }
    }
}

