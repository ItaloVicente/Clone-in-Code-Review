    @Test
    public void shouldFireKeepAlive() throws Exception {
        assumeFalse(TestProperties.isCi());
        final AtomicInteger keepAliveEventCounter = new AtomicInteger();
        final AtomicReference<ChannelHandlerContext> ctxRef = new AtomicReference();

        QueryHandlerV2 testHandler = new QueryHandlerV2(endpoint, responseRingBuffer, queue, false, false) {
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

            @Override
            protected CoreEnvironment env() {
                return DefaultCoreEnvironment.builder()
                    .continuousKeepAliveEnabled(false).build();
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(testHandler);

        testHandler.userEventTriggered(ctxRef.get(), IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT);

        assertEquals(1, keepAliveEventCounter.get());
        assertTrue(queue.peek() instanceof QueryHandlerV2.KeepAliveRequest);
        QueryHandlerV2.KeepAliveRequest keepAliveRequest = (QueryHandlerV2.KeepAliveRequest) queue.peek();

        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
        LastHttpContent responseEnd = new DefaultLastHttpContent();
        channel.writeInbound(response, responseEnd);
        QueryHandlerV2.KeepAliveResponse keepAliveResponse = keepAliveRequest.observable()
            .cast(QueryHandlerV2.KeepAliveResponse.class)
            .timeout(1, TimeUnit.SECONDS).toBlocking().single();

        channel.pipeline().remove(testHandler);
        assertEquals(2, keepAliveEventCounter.get());
        assertEquals(ResponseStatus.NOT_EXISTS, keepAliveResponse.status());
        assertEquals(0, responseEnd.refCnt());
    }
