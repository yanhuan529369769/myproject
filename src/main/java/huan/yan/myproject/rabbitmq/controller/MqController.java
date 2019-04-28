package huan.yan.myproject.rabbitmq.controller;

import huan.yan.myproject.rabbitmq.sender.Sender2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController("/")
public class MqController {

    @Autowired
    private Sender2 sender2;

    @PostMapping("/sendMsgByChannel1")
    public void sendMsg1(String msg) throws IOException {

        sender2.sendByTemplate(msg);

    }
    @PostMapping("/sendMsgByChannel2")
    public void sendMsg2(String msg) throws IOException, InterruptedException {

        sender2.sendByChannelAndConfirm(msg);

    }
    @PostMapping("/sendMsgByChannel3")
    public void sendMsg3(String msg) throws IOException, InterruptedException {

        sender2.sendByChannelAddConfirmListener(msg);

    }

}
