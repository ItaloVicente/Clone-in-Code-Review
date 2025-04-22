
    @Test
    public void shouldFireKeepAlive() throws Exception {
        final AtomicInteger keepAliveEventCounter = new AtomicInteger();
        final AtomicReference<ChannelHandlerContext> ctxRef = new AtomicReference();

        SearchHandler searchKeepAliveHandler = new SearchHandler(endpoint, responseRingBuffer, queue, false, false) {
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
        EmbeddedChannel channel = new EmbeddedChannel(searchKeepAliveHandler);

        searchKeepAliveHandler.userEventTriggered(ctxRef.get(), IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT);

        assertEquals(1, keepAliveEventCounter.get());
        assertTrue(queue.peek() instanceof SearchHandler.KeepAliveRequest);
        SearchHandler.KeepAliveRequest keepAliveRequest = (SearchHandler.KeepAliveRequest) queue.peek();

        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
        LastHttpContent responseEnd = new DefaultLastHttpContent();
        channel.writeInbound(response, responseEnd);
        SearchHandler.KeepAliveResponse keepAliveResponse = keepAliveRequest.observable()
                .cast(SearchHandler.KeepAliveResponse.class)
                .timeout(1, TimeUnit.SECONDS).toBlocking().single();

        channel.pipeline().remove(searchKeepAliveHandler);
        assertEquals(2, keepAliveEventCounter.get());
        assertEquals(ResponseStatus.NOT_EXISTS, keepAliveResponse.status());
        assertEquals(0, responseEnd.refCnt());
    }

