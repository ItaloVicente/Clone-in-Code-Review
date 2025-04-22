    JsonLongDocument counter(String id, long delta);
    JsonLongDocument counter(String id, long delta, long initial);
    JsonLongDocument counter(String id, long delta, long initial, int expiry);
    JsonLongDocument counter(String id, long delta, long timeout, TimeUnit timeUnit);
    JsonLongDocument counter(String id, long delta, long initial, long timeout, TimeUnit timeUnit);
    JsonLongDocument counter(String id, long delta, long initial, int expiry, long timeout, TimeUnit timeUnit);
