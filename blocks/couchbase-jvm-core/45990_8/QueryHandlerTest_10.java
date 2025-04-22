
    @Test
    public void shouldFireKeepAlive() throws Exception {
        final AtomicInteger keepAliveEventCounter = new AtomicInteger();
        final AtomicReference<ChannelHandlerContext> ctxRef = new AtomicReference();

        QueryHandler testHandler = new QueryHandler(endpoint, responseRingBuffer, queue, false) {
            @Override
            public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                super.channelRegistered(ctx);
                ctxRef.compareAndSet(null, ctx);
            }

            @Override
            protected void onKeepAliveFired(ChannelHandlerContext ctx, CouchbaseRequest keepAliveRequest) {
                assertEquals(1, keepAliveEventCounter.incrementAndGet());
            }

            @Override
            protected void onKeepAliveResponse(ChannelHandlerContext ctx, CouchbaseResponse keepAliveResponse) {
                assertEquals(2, keepAliveEventCounter.incrementAndGet());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(testHandler);

        testHandler.userEventTriggered(ctxRef.get(), IdleStateEvent.FIRST_ALL_IDLE_STATE_EVENT);

        assertEquals(1, keepAliveEventCounter.get());
        assertTrue(queue.peek() instanceof QueryHandler.KeepAliveRequest);
        QueryHandler.KeepAliveRequest keepAliveRequest = (QueryHandler.KeepAliveRequest) queue.peek();

        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
        channel.writeInbound(response);
        QueryHandler.KeepAliveResponse keepAliveResponse = keepAliveRequest.observable()
                .cast(QueryHandler.KeepAliveResponse.class)
                .timeout(1, TimeUnit.SECONDS).toBlocking().single();

        assertEquals(2, keepAliveEventCounter.get());
        assertEquals(ResponseStatus.NOT_EXISTS, keepAliveResponse.status());
    }
