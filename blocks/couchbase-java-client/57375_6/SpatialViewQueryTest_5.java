        ctx = CouchbaseTestContext.builder()
                .adhoc(true)
                .bucketName("Spatial")
                .bucketQuota(100)
                .build();

        ctx.ignoreIfClusterUnder(new Version(3, 0, 2));
