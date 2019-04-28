package huan.yan.myproject;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import huan.yan.myproject.rabbitmq.confi.RabbitMQConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyprojectApplicationTests {

	@Autowired
	private RabbitMQConfig.RabbitMQService rabbitMQService;
	@Test
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

}
