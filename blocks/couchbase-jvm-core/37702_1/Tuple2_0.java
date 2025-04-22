package com.couchbase.client.core.lang;

public final class Tuple {

    private Tuple() {
    }

    public static <T1, T2> Tuple2<T1, T2> create(final T1 v1, final T2 v2) {
        return new Tuple2<T1, T2>(v1, v2);
    }

    public static <T1, T2, T3> Tuple3<T1, T2, T3> create(final T1 v1, final T2 v2, final T3 v3) {
        return new Tuple3<T1, T2, T3>(v1, v2, v3);
    }

    public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> create(final T1 v1, final T2 v2, final T3 v3, final T4 v4) {
        return new Tuple4<T1, T2, T3, T4>(v1, v2, v3, v4);
    }

    public static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> create(final T1 v1, final T2 v2, final T3 v3,
        final T4 v4, final T5 v5) {
        return new Tuple5<T1, T2, T3, T4, T5>(v1, v2, v3, v4, v5);
    }


}
