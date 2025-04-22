    LongDocument counter(String id, long delta);
    LongDocument counter(String id, long delta, long initial);
    LongDocument counter(String id, long delta, long initial, int expiry);
    LongDocument counter(String id, long delta, long timeout, TimeUnit timeUnit);
    LongDocument counter(String id, long delta, long initial, long timeout, TimeUnit timeUnit);
    LongDocument counter(String id, long delta, long initial, int expiry, long timeout, TimeUnit timeUnit);
