    protected static TestingClient client = null;
            + ":8091/pools";

    public CouchbaseSetWithoutTTLTest() {
    }

    /**
     * Initialize the client connection.
     *
     * @throws Exception
     */
    protected static void initClient() throws Exception {
        List<URI> uris = new LinkedList<URI>();
        uris.add(URI.create(SERVER_URI));
        client = new TestingClient(uris, "default", "");
    }

    @Before
    public void setUp() throws Exception {
        BucketTool bucketTool = new BucketTool();
        bucketTool.deleteAllBuckets();
        bucketTool.createDefaultBucket(BucketType.COUCHBASE, 256, 0, true);

        BucketTool.FunctionCallback callback = new BucketTool.FunctionCallback() {
            @Override
            public void callback() throws Exception {
                initClient();
            }

            @Override
            public String success(long elapsedTime) {
                return "Client Initialization took " + elapsedTime + "ms";
            }
        };
        bucketTool.poll(callback);
        bucketTool.waitForWarmup(client);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSimpleSetWithNoTTLNoDurability() throws Exception {
        String jsonValue = "{\"name\":\"This is a test with no TTL\"}";
        String jsonValue2 = "NewValue";
        String key001 = "key:001";

        OperationFuture op =  client.set(key001, jsonValue);
        assertTrue( op.getStatus().isSuccess() );
        assertEquals(client.get(key001), jsonValue);
        client.delete(key001);

        op =  client.replace(key001, jsonValue2);
        assertFalse(op.getStatus().isSuccess());

        op =  client.add(key001, jsonValue);
        assertTrue( op.getStatus().isSuccess() );
        assertEquals(client.get(key001),jsonValue);

        op =  client.replace(key001, jsonValue2);
        assertTrue( op.getStatus().isSuccess() );
        assertEquals(client.get(key001),jsonValue2);

        op =  client.add(key001, jsonValue);
        assertFalse( op.getStatus().isSuccess() );

        client.delete(key001);
    }
