package huan.yan.myproject.thread;

public class Demo {

    private static Object object = new Object();

    public static void main(String[] args) throws InterruptedException {


        Thread thread1 = new Thread(() -> {
            synchronized (object) {

                try {
                    System.out.println("111111111111111111111111111111");
                    Thread.sleep(5000);
                    System.out.println("444444444444444444444444");
                    object.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, "thread1");
        Thread thread2 = new Thread(() -> {
            synchronized (object) {
                try {
                    System.out.println("222222222222222222222222222");
                    object.wait();
                    System.out.println("33333333333333333333333333333");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread1");


        thread2.start();
        thread1.start();
        thread1.join();
        thread2.join();
//        object.notify();




    }

}
