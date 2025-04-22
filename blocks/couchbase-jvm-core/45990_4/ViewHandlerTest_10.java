    @Test
    public void shouldFireKeepAlive() throws Exception {
        final AtomicInteger keepAliveEventCounter = new AtomicInteger();
        final AtomicReference<ChannelHandlerContext> ctxRef = new AtomicReference();

        ViewHandler testHandler = new ViewHandler(endpoint, responseRingBuffer, queue, false) {
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
        };
        EmbeddedChannel channel = new EmbeddedChannel(testHandler);

        testHandler.userEventTriggered(ctxRef.get(), IdleStateEvent.FIRST_ALL_IDLE_STATE_EVENT);

        assertEquals(1, keepAliveEventCounter.get());
        assertTrue(queue.peek() instanceof ViewHandler.KeepAliveRequest);
        ViewHandler.KeepAliveRequest keepAliveRequest = (ViewHandler.KeepAliveRequest) queue.peek();

        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
        channel.writeInbound(response);
        ViewHandler.KeepAliveResponse keepAliveResponse = keepAliveRequest.observable()
                .cast(ViewHandler.KeepAliveResponse.class)
                .timeout(1, TimeUnit.SECONDS).toBlocking().single();

        assertEquals(2, keepAliveEventCounter.get());
        assertEquals(ResponseStatus.NOT_EXISTS, keepAliveResponse.status());
    }

