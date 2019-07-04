package huan.yan.myproject;

import com.rabbitmq.client.*;
import huan.yan.myproject.rabbitmq.confi.RabbitMQConfig;
import huan.yan.myproject.zabbix.ConfigUtil;
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

	@Autowired
	private huan.yan.myproject.zabbix.ConfigUtil configUtil;

	@Test
	public void configUtil(){
		System.out.println(configUtil.getJson());


	}

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

	@Test
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
