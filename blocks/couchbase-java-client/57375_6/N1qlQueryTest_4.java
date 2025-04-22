        ctx = CouchbaseTestContext.builder()
                .bucketName("N1qlQuery")
                .adhoc(true)
                .bucketQuota(100)
                .build()
                .ignoreIfNoN1ql();
