    @Test
    public void testUnSubscribedRequest() {
        String id = "key";
        GetRequest request = new GetRequest(id, BUCKET);

        TestSubscriber<CouchbaseResponse> ts = TestSubscriber.create();
        request.subscriber(ts);

        ts.unsubscribe();
        assertTrue(!request.isActive());
    }

    @Test
    public void testRequestSubscription() {
        String id = "key";
        GetRequest request = new GetRequest(id, BUCKET);

        TestSubscriber<CouchbaseResponse> ts = TestSubscriber.create();
        request.subscriber(ts);

        assertTrue(request.isActive());
    }
