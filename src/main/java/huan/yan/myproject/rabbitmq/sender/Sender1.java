/*
package huan.yan.myproject.rabbitmq.sender;

import com.rabbitmq.client.Channel;
import huan.yan.myproject.rabbitmq.confi.RabbitMQConfig;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
public class Sender1 implements RabbitTemplate.ConfirmCallback {


    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Sender1(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);
    }

    public void sendByChannelAndConfirm(String msg) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sendMsg = msg + ":" + time.format(new Date());
        System.out.println("Sender1 : " + sendMsg);
        for (int i = 0; i < 20; i++) {
            this.rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_A, "topic.1.1.1", sendMsg + i, correlationData);
        }
    }

    public void sendByTemplate(String msg) throws IOException {
        System.out.println("Sender : " + msg);
        Channel channel = rabbitTemplate.getConnectionFactory().createConnection().createChannel(false);
        channel.basicPublish("", RabbitMQConfig.QUEUE_B, null, msg.getBytes());
    }


    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("confirm: " + correlationData.getId());
        System.out.println("ack: " + ack);
        System.out.println("cause: " + cause);
    }
}
*/
