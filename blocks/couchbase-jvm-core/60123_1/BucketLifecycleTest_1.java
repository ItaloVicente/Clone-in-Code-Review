    @Test
    public void shouldRemoveBucketWithoutCausingAuthErrors() throws InterruptedException, UnknownHostException {
        final CouchbaseCore core = new CouchbaseCore();

        String testBucket = "testInsertRemoveBucket" + TestProperties.bucket();
        final StringBuilder sb = new StringBuilder();
        sb.append("name=").append(testBucket);
        sb.append("&ramQuotaMB=100&authType=sasl&saslPassword=").append(TestProperties.password());
        sb.append("&replicaNumber=0&proxyPort=0&bucketType=membase&flushEnabled=0");

        SeedNodesRequest seedNodes = new SeedNodesRequest(Arrays.asList(TestProperties.seedNode()));
        OpenBucketRequest openDefault = new OpenBucketRequest(TestProperties.bucket(), TestProperties.password());
        InsertBucketRequest insertAdhoc = new InsertBucketRequest(sb.toString(), TestProperties.adminUser(), TestProperties.adminPassword());
        OpenBucketRequest openAdhoc = new OpenBucketRequest(testBucket, TestProperties.password());
        RemoveBucketRequest removeAdhoc = new RemoveBucketRequest(testBucket, TestProperties.adminUser(), TestProperties.adminPassword());
        CloseBucketRequest closeAdhoc = new CloseBucketRequest(testBucket);
        CloseBucketRequest closeDefault = new CloseBucketRequest(TestProperties.bucket());

        assertEquals("Error seeding nodes", ResponseStatus.SUCCESS, core.send(seedNodes).toBlocking().single().status());
        assertEquals("Error opening default bucket", ResponseStatus.SUCCESS, core.send(openDefault).toBlocking().single().status());
        assertEquals("Error inserting adhoc", ResponseStatus.SUCCESS, core.send(insertAdhoc).toBlocking().single().status());
        Thread.sleep(2000);
        assertEquals("Error opening adhoc", ResponseStatus.SUCCESS, core.send(openAdhoc).toBlocking().single().status());
        assertEquals("Error closing adhoc", ResponseStatus.SUCCESS, core.send(closeAdhoc).toBlocking().single().status());

        final InetAddress seedAddress = InetAddress.getByName(TestProperties.seedNode());
        core.<AddNodeResponse>send(new AddNodeRequest(seedAddress))
                        .flatMap(new Func1<AddNodeResponse, Observable<AddServiceResponse>>() {
                            @Override
                            public Observable<AddServiceResponse> call(AddNodeResponse response) {
                                return core.send(new AddServiceRequest(ServiceType.CONFIG, TestProperties.adminUser(),
                                        TestProperties.adminPassword(),8091, seedAddress));
                            }
                        })
                .toBlocking().single();
        assertEquals("Error removing adhoc", ResponseStatus.SUCCESS, core.send(removeAdhoc).toBlocking().single().status());

        assertEquals("Error closing default", ResponseStatus.SUCCESS, core.send(closeDefault).toBlocking().single().status());

        core.send(new DisconnectRequest());
    }

