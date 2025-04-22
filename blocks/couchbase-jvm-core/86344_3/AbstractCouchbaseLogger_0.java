
package com.couchbase.client.core.lang.backport.java.util;

public class Objects {
    private Objects() {
        throw new AssertionError("not instantiable");
    }

    public static <T> T requireNonNull(T object) {
        if (object == null) {
            throw new NullPointerException();
        }
        return object;
    }

    public static <T> T requireNonNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }
}
