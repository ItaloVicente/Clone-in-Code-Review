    @Test
    public void shouldSendKeepaliveIfEmpty() {
        assertTrue(handler.shouldSendKeepAlive());
    }

    @Test
    public void shouldSendKeepaliveIfActiveInFlight() {
        Subscriber subscriber = new TestSubscriber();
        GetRequest request = new GetRequest("key", "bucket");
        request.subscriber(subscriber);
        requestQueue.add(request);
        channel.writeOutbound();
        assertTrue(handler.shouldSendKeepAlive());
    }

    @Test
    public void shouldSendKeepaliveIfInactiveInFlight() {
        Subscriber subscriber = new TestSubscriber();
        GetRequest request = new GetRequest("key", "bucket");
        request.subscriber(subscriber);
        requestQueue.add(request);
        channel.writeOutbound();
        assertTrue(handler.shouldSendKeepAlive());
        subscriber.unsubscribe();
        assertTrue(handler.shouldSendKeepAlive());
    }

