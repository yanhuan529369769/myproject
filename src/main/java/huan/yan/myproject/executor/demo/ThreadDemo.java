package huan.yan.myproject.executor.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadDemo {


    private static final ExecutorService executor = Executors.newFixedThreadPool(5);

    public static void  main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(args);
        /*long l = System.currentTimeMillis();
        List<Future> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int finalI = i;
            Future<?> submit = executor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + ":" + finalI);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("submit:" + submit.isDone());
            list.add(submit);
        }
        Thread.sleep(10000);
        long l2 = System.currentTimeMillis();
        System.out.println("cost time:" + (l2 - l));
        for (Future future : list) {
            System.out.println(future.isDone());
        }*/
        List<Future> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int finalI = i;
            Future<?> submit = executor.submit(new Callable() {

                @Override
                public Object call() throws Exception {
                    if(finalI % 2 ==0){
                        return 1;
                    }else {
                        return 0;
                    }
                }
            });


        }
    }
}

