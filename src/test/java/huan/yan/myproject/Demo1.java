package huan.yan.myproject;

import java.util.Scanner;

public class Demo1 {

    public static void main(String[] args) {

        int[] arr= {1111,4112,53,1123,631,124,1245};
        for (int i = 1; i < arr.length; i++) {


        }



    }

    public static String reverseString(String s) {
        String str = "";
        char[] chars1 = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            chars1[s.length() - i - 1] = s.charAt(i);
        }
        for (int j = 0; j < s.length(); j++) {
            str += chars1[j];
        }
        return str;
    }

}
