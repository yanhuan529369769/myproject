package huan.yan.myproject.thread;

public interface Formula {

    double calculate(int a);

    default double sqrt(int a) {
        return Math.sqrt(a);
    }

    public static void main(String[] args) {

        Formula formula = new Formula() {
            @Override
            public double calculate(int a) {
                return 0;
            }
        };

        System.out.println(formula.sqrt(111));

    }

}
