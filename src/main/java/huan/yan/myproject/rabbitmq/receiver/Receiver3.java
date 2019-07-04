package huan.yan.myproject.rabbitmq.receiver;

import com.rabbitmq.client.*;
import huan.yan.myproject.rabbitmq.confi.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Receiver3 {

    @Autowired
    private RabbitMQConfig.RabbitMQService rabbitMQService;

    public void contextLoads() throws IOException {
        Channel channel = rabbitMQService.getChannel();
        while(true){
            String s = channel.basicConsume(RabbitMQConfig.QUEUE_B, false, new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    System.out.println("Receiver3.consumerTag:" + consumerTag);
                    System.out.println("Receiver3.envelope:" + envelope);
                    System.out.println("Receiver3.properties:" + properties);
                    System.out.println("Receiver3.body:" + new String(body));

                    channel.basicAck(envelope.getDeliveryTag(), false);

                }
            });
        }
    }


    public void deliverCallback() throws IOException {
        Channel channel = rabbitMQService.getChannel();
        while(true){
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(RabbitMQConfig.QUEUE_B, true, deliverCallback, consumerTag -> { });
        }
    }
}
