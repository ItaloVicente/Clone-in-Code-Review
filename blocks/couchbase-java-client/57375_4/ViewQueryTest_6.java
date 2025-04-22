        ctx = CouchbaseTestContext.builder()
            .adhoc(true)
            .bucketQuota(100)
            .bucketName("View")
            .build();

