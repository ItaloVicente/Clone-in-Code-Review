    public static void init() {
        ctx = CouchbaseTestContext.builder()
                .bucketName("N1qlDml")
                .adhoc(true)
                .bucketQuota(100)
                .build()
            .ignoreIfNoN1ql();
    }

    @AfterClass
    public static void cleanup() {
        ctx.destroyBucketAndDisconnect();
