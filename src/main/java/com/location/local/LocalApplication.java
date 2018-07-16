package com.location.local;

import com.location.local.dao.UserDao;
import com.location.local.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.test.context.jdbc.Sql;

import com.location.local.SocketServer;
import java.net.ServerSocket;
import java.net.Socket;


//外网ip：202.104.27.84:3080----->内网ip:172.21.102.7:8081
//外网ip：202.104.27.84:3084----->内网ip:172.21.102.7:10006
@SpringBootApplication
@Component("com.location.local")
//@Sql("/init-schema.sql")
public class LocalApplication {


    public static void main(String[] args) throws Exception{

        SpringApplication.run(LocalApplication.class, args);


        ServerSocket server = new ServerSocket(10006);
        Socket client = null;
        //循环监听等待客户端的连接
        while(true){
            //等待客户端的连接，如果没有获取连接
            client = server.accept();
            //String ip=client.getLocalAddress().getHostAddress();;
            System.out.println("与客户端连接成功！");
            //为每个客户端连接开启一个线程
            new Thread(new SocketServer(client)).start();
            //System.out.println("连接设备的ip为:"+ip);
        }
    }
}
