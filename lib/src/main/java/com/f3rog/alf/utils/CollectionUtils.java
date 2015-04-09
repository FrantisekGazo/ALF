package com.f3rog.alf.utils;

import java.util.List;

/**
 * Class {@link CollectionUtils} contains various functions for working with collections.
 *
 * @author f3rog
 * @version 2015-03-08
 */
public class CollectionUtils {

    public static <T> String concat(T[] array, String separator) {
        String ret = "";
        for (int i = 0; i < array.length; i++) {
            ret += array[i];
            if (i < array.length - 1) {
                ret += separator;
            }
        }
        return ret;
    }

    public static String concat(List<?> array, String separator) {
        String ret = "";
        if (array != null) {
            for (int i = 0; i < array.size(); i++) {
                ret += array.get(i);
                if (i < array.size() - 1) {
                    ret += separator;
                }
            }
        }
        return ret;
    }

}
