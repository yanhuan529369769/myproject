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


/*public class Receiver3 {

    public static void main(String[] args) throws IOException {
        RabbitMQConfig config = new RabbitMQConfig();
        RabbitMQConfig.RabbitMQService rabbitMQService = new RabbitMQConfig().new RabbitMQService();
        Channel channel = rabbitMQService.getChannel();
        while(true){
            String s = channel.basicConsume(RabbitMQConfig.QUEUE_B, false, new DefaultConsumer(channel));

            System.out.println("Receiver3.getMsg():" + s);
        }
    }

}*/
