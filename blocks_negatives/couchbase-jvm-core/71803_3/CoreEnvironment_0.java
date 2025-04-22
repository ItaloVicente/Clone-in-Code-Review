
    /**
     * Allows to specify a custom strategy to hash memcached bucket documents.
     *
     * If you want to use this SDK side by side with 1.x SDKs on memcached buckets, configure the
     * environment to use the {@link com.couchbase.client.core.node.LegacyMemcachedHashingStrategy} instead.
     *
     * @return the memcached hashing strategy.
     */
    MemcachedHashingStrategy memcachedHashingStrategy();
