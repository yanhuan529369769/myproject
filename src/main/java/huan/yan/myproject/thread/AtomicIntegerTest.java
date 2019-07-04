package huan.yan.myproject.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {

    private static AtomicInteger atomicInteger = new AtomicInteger();

    private static int a = 0;

    private static ExecutorService executor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {

            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + ":=====:" + atomicInteger.getAndIncrement());
            });

        }

        executor.shutdown();


    }





}
