package huan.yan.myproject;

import huan.yan.myproject.listener.MyStartEventAppListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyprojectApplication {

	public static void main(String[] args) {
//		SpringApplication.run(huan.yan.myproject.MyprojectApplication.class, args);
        SpringApplication application = new SpringApplication(MyprojectApplication.class);

        application.addListeners(new MyStartEventAppListener());

        application.run(args);

	}

}
