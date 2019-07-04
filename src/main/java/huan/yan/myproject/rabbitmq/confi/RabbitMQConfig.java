package huan.yan.myproject.rabbitmq.confi;

import com.rabbitmq.client.Channel;
import huan.yan.myproject.rabbitmq.receiver.Receiver2;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@SuppressWarnings("all")
@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private  String addresses;

    @Value("${spring.rabbitmq.port}")
    private  String port;

    @Value("${spring.rabbitmq.username}")
    private  String username;

    @Value("${spring.rabbitmq.password}")
    private  String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private  String virtualHost;

    @Value("${spring.rabbitmq.publisher-confirms}")
    private  boolean publisherConfirms;

    public static final String EXCHANGE_A = "my-mq-exchange_A";
    public static final String EXCHANGE_B = "my-mq-exchange_B";
    public static final String EXCHANGE_C = "my-mq-exchange_C";


    public static final String QUEUE_A = "QUEUE_A";
    public static final String QUEUE_B = "QUEUE_B";
    public static final String QUEUE_C = "QUEUE_C";

    public static final String ROUTINGKEY_A = "spring-boot-routingKey_A";
    public static final String ROUTINGKEY_B = "spring-boot-routingKey_B";
    public static final String ROUTINGKEY_C = "spring-boot-routingKey_C";

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        // 必须设置为 true，不然当 发送到交换器成功，但是没有匹配的队列，不会触发 ReturnCallback 回调
        // 而且 ReturnCallback 比 ConfirmCallback 先回调，意思就是 ReturnCallback 执行完了才会执行 ConfirmCallback
        rabbitTemplate.setMandatory(true);

        // 设置 ConfirmCallback 回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            // 如果发送到交换器都没有成功（比如说删除了交换器），ack 返回值为 false
            // 如果发送到交换器成功，但是没有匹配的队列（比如说取消了绑定），ack 返回值为还是 true （这是一个坑，需要注意）
            if (ack) {
 String id = correlationData.getId();
                boolean ackFlag = (boolean) correlationData.getReturnedMessage().getMessageProperties().getHeaders().get(id);
                if (ackFlag) {
                    System.out.println("rabbitTemplate setConfirmCallback correlationData:" + correlationData);
                }


                System.out.println("rabbitTemplate setConfirmCallback correlationData:" + correlationData);
                System.out.println("rabbitTemplate setConfirmCallback ack:" + ack);
                System.out.println("rabbitTemplate setConfirmCallback cause:" + cause);
            }
        });

        // 设置 ReturnCallback 回调
        // 如果发送到交换器成功，但是没有匹配的队列，就会触发这个回调
        rabbitTemplate.setReturnCallback((message, replyCode, replyText,
                                          exchange, routingKey) -> {
            MessageProperties messageProperties = message.getMessageProperties();
            String messageId = messageProperties.getMessageId();
            Map<String, Object> headers = messageProperties.getHeaders();
            headers.put(messageId, true);
            System.out.println("rabbitTemplate setReturnCallback message:" + message);
        });
        return rabbitTemplate;
    }

    @Bean
    public Queue queue1() {
        return new Queue(QUEUE_A, true, false, false);
    }

    @Bean
    public Queue queue2() {
        return new Queue(QUEUE_B, true, false, false);
    }

    @Bean
    public Queue queue3() {
        return new Queue(QUEUE_C, true, false, false);
    }

    @Bean()
    public TopicExchange exchange() {

        return new TopicExchange(EXCHANGE_A, true, false, null);
    }

    @Bean()
    public TopicExchange exchange2() {

        return new TopicExchange(EXCHANGE_B, true, false, null);
    }

    //  配置连接工厂
    @Bean
    public ConnectionFactory connectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(addresses + ":" + port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
//* 如果要进行消息回调，则这里必须要设置为true

        connectionFactory.setPublisherConfirms(publisherConfirms);
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }

    // 配置接收端属性
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // factory.setPrefetchCount(5);//这个参数设置，接收消息端，接收的最大消息数量（包括使用get、consume）,一旦到达这个数量，客户端不在接收消息。0为不限制。默认值为3.
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);// 确认模式：自动，默认
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());// 接收端类型转化pojo,需要序列化
        return factory;
    }

    //创建监听器，监听队列
    @Bean
    public SimpleMessageListenerContainer mqMessageContainer(@Autowired Receiver2 handleService) throws AmqpException, IOException {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        container.setQueueNames(queue1().getName(), queue2().getName());
        container.setExposeListenerChannel(true);
//        container.setPrefetchCount(prefetchCount);//设置每个消费者获取的最大的消息数量
//        container.setConcurrentConsumers(concurrentConsumers);//消费者个数
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);//设置确认模式为手工确认
        container.setMessageListener(handleService);//监听处理类
        return container;
    }


    @Bean
    public Binding bind() {
        return BindingBuilder.bind(queue1()).to(exchange()).with("topic.*");
    }

    @Bean
    public Binding bindA1() {
        return BindingBuilder.bind(queue2()).to(exchange()).with("topic.#");
    }

 @Bean
     public Binding bind1() {
         return BindingBuilder.bind(queue2()).to(exchange()).with("topic.#");
     }

     @Bean
     public Binding bind2() {
         return BindingBuilder.bind(queue3()).to(exchange()).with("topic.#");
     }


    @Component
    public class RabbitMQService {
        private Connection connection;
        public Channel channel;
        private ConnectionFactory factory = connectionFactory();

        public RabbitMQService() {
            connection = getConnection(factory);
            channel = getChannel();
        }

        private Connection getConnection(ConnectionFactory factory) {
            if (connection == null || !connection.isOpen()) {
                connection = factory.createConnection();
            }
            return connection;
        }

        public Channel getChannel() {
            channel = connection.createChannel(false);
            return channel;
        }
    }
}
