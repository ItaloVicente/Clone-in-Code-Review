    @BeforeClass
    public static void connect() {
        cluster = new CouchbaseCluster();
        cluster.<SeedNodesResponse>send(new SeedNodesRequest(seedNode)).flatMap(
                new Func1<SeedNodesResponse, Observable<OpenBucketResponse>>() {
                    @Override
                    public Observable<OpenBucketResponse> call(SeedNodesResponse response) {
                        return cluster.send(new OpenBucketRequest(bucket, password));
                    }
                }
        ).toBlockingObservable().single();
    }

    @Test
    public void shouldUpsertAndGetDocument() {
        String key = "upsert-key";
        String content = "Hello World!";
        UpsertRequest upsert = new UpsertRequest(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), bucket);
        cluster.<UpsertResponse>send(upsert).toBlockingObservable().single();
