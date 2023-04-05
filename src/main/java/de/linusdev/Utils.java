package de.linusdev;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Utils {

    public static Unsafe UNSAFE;

    static {
        Field f = null;
        try {
            f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            UNSAFE = (Unsafe) f.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

}
