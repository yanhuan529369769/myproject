/*
package huan.yan.myproject.rabbitmq.confi;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SuppressWarnings("all")

public class RabbitMQService {
    private Connection connection;
    public Channel channel;
    @Autowired
    private ConnectionFactory factory;

    private RabbitMQService() {
        connection = getConnection(factory);
        channel=getChannel();
    }

    private Connection getConnection(ConnectionFactory factory) {
        try {
            if (connection == null || !connection.isOpen()) {
                connection = factory.newConnection();
            }
            return connection;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Channel getChannel(){
        try {
            if (channel == null || !channel.isOpen() || !connection.isOpen()) {
                channel = connection.createChannel();
            }
            return channel;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }





}
*/
