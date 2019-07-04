package huan.yan.myproject.task;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@EnableScheduling
public class TaskRun {

    @Scheduled(cron = "0/1 * * * * ? ")
    private void cancelTimeOutOrder(){
        System.out.println("TaskRun start:" + new Date());
    }

}
