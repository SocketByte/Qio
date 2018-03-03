package net.qio.lang.utilities;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PrintUtilities {

    public static void printmh(String text) {
        try {
            System.out.println(MathUtilities.eval(text));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void println(Object text) {
        System.out.println(text);
    }

}
