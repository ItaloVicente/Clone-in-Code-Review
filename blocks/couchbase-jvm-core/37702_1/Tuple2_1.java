package com.couchbase.client.core.lang;

public final class Tuple2<T1, T2> {

    private final T1 value1;

    private final T2 value2;

    Tuple2(final T1 value1, final T2 value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public T1 value1() {
        return value1;
    }

    public T2 value2() {
        return value2;
    }

    public Tuple2<T2, T1> swap() {
        return new Tuple2<T2, T1>(value2, value1);
    }

}
