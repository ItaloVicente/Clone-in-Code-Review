
    @Test
    public void shouldSendKeepaliveIfEmpty() {
        assertTrue(handler.shouldSendKeepAlive());
    }

    @Test
    public void shouldNotSendKeepaliveIfActiveInFlight() {
        Subscriber subscriber = new TestSubscriber();
        GenericQueryRequest request = GenericQueryRequest.simpleStatement("select 1=1", "bucket", "pw");
        request.subscriber(subscriber);
        queue.add(request);
        channel.writeOutbound();
        assertFalse(handler.shouldSendKeepAlive());
    }

    @Test
    public void shouldSendKeepaliveIfInactiveInFlight() {
        Subscriber subscriber = new TestSubscriber();
        GenericQueryRequest request = GenericQueryRequest.simpleStatement("select 1=1", "bucket", "pw");
        request.subscriber(subscriber);
        queue.add(request);
        channel.writeOutbound();
        assertFalse(handler.shouldSendKeepAlive());
        subscriber.unsubscribe();
        assertTrue(handler.shouldSendKeepAlive());
    }
