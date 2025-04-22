package com.couchbase.client.core.utils;

public class MathUtils {

    public static long floorMod(long x, long y) {
        return x - floorDiv(x, y) * y;
    }

    public static long floorDiv(long x, long y) {
        long r = x / y;
        if ((x ^ y) < 0 && (r * y != x)) {
            r--;
        }
        return r;
    }
}
