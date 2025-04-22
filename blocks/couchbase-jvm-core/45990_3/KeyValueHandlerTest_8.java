    @Test
    public void shouldFireKeepAlive() throws Exception {
        final AtomicInteger keepAliveEventCounter = new AtomicInteger();
        final AtomicReference<ChannelHandlerContext> ctxRef = new AtomicReference();

        KeyValueHandler testHandler = new KeyValueHandler(mock(AbstractEndpoint.class), eventSink,
                requestQueue, false) {

            @Override
            public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                super.channelRegistered(ctx);
                ctxRef.compareAndSet(null, ctx);
            }

            @Override
            protected void onKeepAliveFired(CouchbaseRequest keepAliveRequest) {
                assertEquals(1, keepAliveEventCounter.incrementAndGet());
            }

            @Override
            protected void onKeepAliveResponse(CouchbaseResponse keepAliveResponse) {
                assertEquals(2, keepAliveEventCounter.incrementAndGet());
            }

            @Override
            protected CoreEnvironment env() {
                return DefaultCoreEnvironment.create();
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(testHandler);

        testHandler.userEventTriggered(ctxRef.get(), IdleStateEvent.FIRST_ALL_IDLE_STATE_EVENT);

        assertEquals(1, keepAliveEventCounter.get());
        assertTrue(requestQueue.peek() instanceof KeyValueHandler.KeepAliveRequest);
        KeyValueHandler.KeepAliveRequest keepAliveRequest = (KeyValueHandler.KeepAliveRequest) requestQueue.peek();

        DefaultFullBinaryMemcacheResponse response = new DefaultFullBinaryMemcacheResponse("", Unpooled.EMPTY_BUFFER);
        response.setOpaque(keepAliveRequest.opaque());
        response.setStatus(KeyValueHandler.ERR_NO_MEM);
        channel.writeInbound(response);
        KeyValueHandler.KeepAliveResponse keepAliveResponse = keepAliveRequest.observable()
                .cast(KeyValueHandler.KeepAliveResponse.class)
                .timeout(1, TimeUnit.SECONDS).toBlocking().single();

        assertEquals(2, keepAliveEventCounter.get());
        assertEquals(ResponseStatus.OUT_OF_MEMORY, keepAliveResponse.status());
    }

