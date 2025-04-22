package com.couchbase.client.core.lang;

public final class Tuple3<T1, T2, T3> {

    private final T1 value1;

    private final T2 value2;

    private final T3 value3;

    Tuple3(final T1 value1, final T2 value2, final T3 value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public T1 value1() {
        return value1;
    }

    public T2 value2() {
        return value2;
    }

    public T3 value3() {
        return value3;
    }
}
