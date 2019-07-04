package huan.yan.myproject;


public class ClassLoader {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//        java.lang.ClassLoader classLoader = Demo1.class.getClassLoader();
/*        java.lang.ClassLoader classLoader = JUnit4.class.getClassLoader();
        System.out.println(classLoader);

        java.lang.ClassLoader parent = classLoader.getParent();

        System.out.println(parent);

        java.lang.ClassLoader parent1 = parent.getParent();

        System.out.println(parent1);*/

        Class<?> aClass = Class.forName("huan.yan.myproject.Demo1", false, ClassLoader.class.getClassLoader());
        System.out.println(aClass.getClassLoader());


    }
}
