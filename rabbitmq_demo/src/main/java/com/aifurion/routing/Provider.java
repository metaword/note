package com.aifurion.routing;

import com.aifurion.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author ：zzy
 * @description：TODO
 * @date ：2021/4/13 10:47
 */
public class Provider {
    public static void main(String[] args) {
        try {
            //获取连接对象
            Connection connection = ConnectionUtil.getConnection();
            //获取通道对象
            Channel channel = connection.createChannel();
            //创建交换机（交换机没有存储数据的能力，数据存储在队列上，如果有交换机没队列的情况下，数据会丢失）   //1.参数一：交换机名称    参数二：交换机类型
            channel.exchangeDeclare("direct_exchange", "direct");
            //向队列中发送消息
            for (int i = 1; i <= 10; i++) {
                channel.basicPublish("direct_exchange",
                        "insert",  //设置路由键，符合路由键的队列，才能拿到消息
                        null,
                        ("Hello RabbitMQ!!!" + i).getBytes());
            }
            //断开连接
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}