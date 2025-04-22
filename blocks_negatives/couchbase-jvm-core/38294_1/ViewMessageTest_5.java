    @BeforeClass
    public static void connect() {
        cluster = new CouchbaseCore();
        cluster.<SeedNodesResponse>send(new SeedNodesRequest(seedNode)).flatMap(
            new Func1<SeedNodesResponse, Observable<OpenBucketResponse>>() {
                @Override
                public Observable<OpenBucketResponse> call(SeedNodesResponse response) {
                    return cluster.send(new OpenBucketRequest(bucket, password));
                }
            }
        ).toBlocking().single();
    }

    @AfterClass
    public static void disconnect() throws InterruptedException {
        cluster.send(new DisconnectRequest()).toBlocking().first();
    }

    /*@Test
    public void shoudQueryNonExistentView() {
        ViewQueryResponse single = cluster
            .<ViewQueryResponse>send(new ViewQueryRequest("design", "view", false, bucket, password))
