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
        assertTrue(requestQueue.peek() instanceof KeyValueHandler.KeepAliveRequest);
        KeyValueHandler.KeepAliveRequest keepAliveRequest = (KeyValueHandler.KeepAliveRequest) requestQueue.peek();

        DefaultFullBinaryMemcacheResponse response = new DefaultFullBinaryMemcacheResponse(new byte[] {}, Unpooled.EMPTY_BUFFER);
        response.setOpaque(keepAliveRequest.opaque());
        response.setStatus(KeyValueStatus.ERR_NO_MEM.code());

        channel.writeInbound(response);
        KeyValueHandler.KeepAliveResponse keepAliveResponse = keepAliveRequest.observable()
