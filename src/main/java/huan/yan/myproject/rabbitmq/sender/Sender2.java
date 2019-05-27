package huan.yan.myproject.rabbitmq.sender;

import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.AMQBasicProperties;
import com.rabbitmq.client.impl.ContentHeaderPropertyWriter;
import huan.yan.myproject.rabbitmq.confi.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;

@Component
@SuppressWarnings("all")
public class Sender2 {

    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    @Autowired
    private RabbitMQConfig.RabbitMQService rabbitMQService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendByTemplate(String msg) throws IOException {
        System.out.println("Sender : " + msg);
        Channel channel = rabbitMQService.channel;
//        channel.basicPublish(RabbitMQConfig.EXCHANGE_A, "topic.a.b", null, msg.getBytes());

//        rabbitTemplate.sendByTemplate(RabbitMQConfig.EXCHANGE_A, "topic.a.b", new Message(msg.getBytes(), new MessageProperties()), null);

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_A, "topic.a.b", msg);

    }

    public void sendByChannelAndConfirm(String msg) throws IOException, InterruptedException {
        System.out.println("Sender : " + msg);
        Channel channel = rabbitMQService.channel;
        //  单条同步消息发送确认手动  waitForConfirms会阻塞
        channel.confirmSelect();
        long l = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            channel.basicPublish(RabbitMQConfig.EXCHANGE_A, "topic.a.b", null, (msg + i).getBytes());
        }

        if (channel.waitForConfirms()) {
            System.out.println("发送成功");
        }
        long l2 = System.currentTimeMillis();
        System.out.println("sendByChannelAndConfirm time:" + (l2 - l));
    }

    public void sendByChannelAddConfirmListener(String msg) throws IOException, InterruptedException {
        Set redis = new TreeSet();
        System.out.println("Sender : " + msg);
        Channel channel = rabbitMQService.channel;
        String uuid = UUID.randomUUID().toString().replace("-", "");
        // channel上添加监听器
        channel.confirmSelect();
        long l = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            channel.basicPublish(RabbitMQConfig.EXCHANGE_A, "topic.a.b", null, (msg + i).getBytes());
           /* AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                    .deliveryMode(2) // 传送方式
                    .contentEncoding("UTF-8") // 编码方式
                    .expiration("10000") // 过期时间
                    .headers(null)//自定义属性
                    .build();
            redis.add(msg+i);
            Thread.sleep(2000);*/
        }
        channel.addConfirmListener(new ConfirmListener() {

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("nack: deliveryTag = " + deliveryTag + " multiple: " + multiple);


            }

            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("ack: deliveryTag = " + deliveryTag + " multiple: " + multiple);

            }
        });
        long l2 = System.currentTimeMillis();
        System.out.println("sendByChannelAddConfirmListener time:" + (l2 - l));
    }

    //  producer方添加事务，但是效率很低 不如confirm模式
    public void sendByChannelAddTransaction(String msg) throws IOException, TimeoutException {
        Channel channel = rabbitMQService.channel;
        try {
            channel.txSelect();


            channel.txCommit();
        } catch (Exception e) {
            channel.txRollback();
            e.printStackTrace();
        } finally {
            channel.close();
        }
    }


}
