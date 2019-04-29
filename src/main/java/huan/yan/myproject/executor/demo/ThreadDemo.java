package huan.yan.myproject.executor.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadDemo {


    private static final ExecutorService executor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long l = System.currentTimeMillis();
        List<Future> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int finalI = i;
            Future<?> submit = executor.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ":" + finalI);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
//            System.out.println("submit:" + submit.get());
            list.add(submit);
        }
        long l2 = System.currentTimeMillis();
        System.out.println("cost time:" + (l2 - l));
        System.out.println("list:" + list);
    }

}

