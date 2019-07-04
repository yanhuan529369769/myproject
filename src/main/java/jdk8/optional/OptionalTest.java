package jdk8.optional;

import com.rabbitmq.client.BlockedCallback;
import com.rabbitmq.client.UnblockedCallback;

import java.util.*;
import java.util.function.Function;

public class OptionalTest<T> {

    private T value;

    public static void main(String[] args) {
        User user = new User();
        user.setId(111);
        user.setName("abb");
        user = null;
        /*Optional<User> user1 = Optional.ofNullable(user);

        String user_is_null = Optional.of(user1).flatMap(user2 -> user1.map(user3 -> user3.getName())).orElse("user " +
                "is null");

        System.out.println(user_is_null);*/

        String s = Optional.of(user).map(user1 -> user1.getName()).orElse("user " +
                "is null");
        System.out.println(s);


    }

}

class User {
    private String name;
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static void print(User user) {

        print(user);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    public static void main(String[] args) {


/*        List<String> stringList = new ArrayList<>();
        stringList.add("ddd2");
        stringList.add("aaa2");
        stringList.add("bbb1");
        stringList.add("aaa1");
        stringList.add("bbb3");
        stringList.add("ccc");
        stringList.add("bbb2");
        stringList.add("ddd1");

        stringList.stream().map(String::toUpperCase).forEach(System.out::print);*/

        /*Map map = new HashMap();

        map.put("a", "bb");
        map.merge("a", "concat", (value, newValue) -> value);
        System.out.println(map.get("a"));*/

        int a =10;
//        int b=a;
        int b =10;
        String str1 = "myString";
        String str2 = "myString";

        System.out.println(str1==str2);

    }
}
