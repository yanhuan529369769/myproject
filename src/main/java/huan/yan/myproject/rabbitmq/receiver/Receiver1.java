package huan.yan.myproject.rabbitmq.receiver;

import huan.yan.myproject.rabbitmq.confi.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class Receiver1 {

    private final ExecutorService executor = Executors.newFixedThreadPool(5);


    @RabbitListener(queues= RabbitMQConfig.QUEUE_A,concurrency = "2")
    public void process1(Message message) throws InterruptedException {
        executor.execute(() -> {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + "====" + message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + "====" + message);
    }



}
