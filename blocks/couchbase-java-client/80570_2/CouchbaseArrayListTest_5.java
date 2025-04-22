        ctx = CouchbaseTestContext.builder()
            .bucketQuota(100)
            .bucketReplicas(1)
            .bucketType(BucketType.COUCHBASE)
            .build();

        ctx.ignoreIfMissing(CouchbaseFeature.SUBDOC);
