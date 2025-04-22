    AsyncBucketManager async();

    BucketInfo info();

    BucketInfo info(long timeout, TimeUnit timeUnit);

    Boolean flush();

    Boolean flush(long timeout, TimeUnit timeUnit);

