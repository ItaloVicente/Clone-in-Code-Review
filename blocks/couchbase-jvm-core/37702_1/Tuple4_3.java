package com.couchbase.client.core.lang;

public final class Tuple4<T1, T2, T3, T4> {

    private final T1 value1;

    private final T2 value2;

    private final T3 value3;

    private final T4 value4;

    Tuple4(final T1 value1, final T2 value2, final T3 value3, final T4 value4) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
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

    public T4 value4() {
        return value4;
    }

}
