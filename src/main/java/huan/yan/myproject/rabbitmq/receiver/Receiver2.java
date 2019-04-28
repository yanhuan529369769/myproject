package huan.yan.myproject.rabbitmq.receiver;

import com.rabbitmq.client.Channel;
import huan.yan.myproject.rabbitmq.confi.RabbitMQConfig;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component()
@Scope("prototype")
public class Receiver2 implements ChannelAwareMessageListener {

    private final ExecutorService executor = Executors.newFixedThreadPool(5);


    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("Receiver2:" + message.toString());
        MessageProperties messageProperties = message.getMessageProperties();
        long deliveryTag = messageProperties.getDeliveryTag();
        // ack消费确认
        channel.basicAck(deliveryTag, false);
    }

}
