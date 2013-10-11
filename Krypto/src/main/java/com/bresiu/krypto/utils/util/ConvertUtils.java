package com.bresiu.krypto.utils.util;

import java.util.List;

public class ConvertUtils {
    /**
     * Get a array from list
     */
    public static int[] listToArray(List<Integer> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) array[i] = list.get(i);
        return array;
    }
}
