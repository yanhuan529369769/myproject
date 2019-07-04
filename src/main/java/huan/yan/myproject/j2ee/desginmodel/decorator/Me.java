package huan.yan.myproject.j2ee.desginmodel.decorator;

public class Me implements Person {
    @Override
    public void say() {
        System.out.println("i can say english");
    }

    @Override
    public void run() {
        System.out.println("i run");
    }
}
