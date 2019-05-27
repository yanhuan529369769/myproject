package huan.yan.myproject.thread;

public class PossibleReordering {
    static int x = -1, y = -1;
    static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            Thread one = new Thread(new Runnable() {
                public void run() {
                    System.out.println(1);
                    a = 1;
                    System.out.println(2);
                    x = b;
                    System.out.println(3);
                }
            });

            Thread other = new Thread(new Runnable() {
                public void run() {
                    System.out.println(4);
                    b = 1;
                    System.out.println(5);
                    y = a;
                    System.out.println(6);
                }
            });
            one.start();
            other.start();
            one.join();
            other.join();
            System.out.println("(" + x + "," + y + ")");
            if(x==0 && y==0){
                break;
            }
        }
    }
}
