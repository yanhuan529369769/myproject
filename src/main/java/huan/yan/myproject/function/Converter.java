package huan.yan.myproject.function;

@FunctionalInterface
public interface Converter<F, T> {
    T convert(F from);

    public static void main(String[] args) {

//        Converter<String, Integer> converter = (i) -> Integer.parseInt(i);
        Converter<String, Integer> converter = Integer::parseInt;

        System.out.println(converter.convert("1111" ));





    }
}

class Something {
    String startsWith(String s) {
        return String.valueOf(s.charAt(0));
    }
}