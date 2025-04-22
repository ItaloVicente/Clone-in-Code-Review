                .bucketPassword("protected")
                .adhoc(true)
                .bucketQuota(100)
                .build()
                .ignoreIfNoN1ql()
                .ensurePrimaryIndex();

        ctx3 = CouchbaseTestContext.builder()
                .bucketName("N1qlCluster3")
