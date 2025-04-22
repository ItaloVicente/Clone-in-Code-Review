
    if(mconn instanceof CouchbaseMemcachedConnection) {
      throw new IllegalArgumentException("Durability options are not supported"
        + " on memcached type buckets.");
    }

    OperationFuture<CASResponse> casOp =
      asyncCAS(key, cas, exp, value, transcoder);
