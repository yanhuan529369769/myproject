package huan.yan.myproject.j2ee.desginmodel.decorator;

public abstract class SuperMe implements Person{

    private Person person;

    @Override
    public void say() {
        person.say();
        System.out.println("i can sar chianese");
    }


    public static void main(String[] args) {



    }
}
