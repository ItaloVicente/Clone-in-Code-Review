    @Test
    public void shouldObserveThroughTokens() {
        InsertRequest request = new InsertRequest("persInsDoc5", Unpooled.copiedBuffer("test", CharsetUtil.UTF_8), bucket());
        InsertResponse response = cluster().<InsertResponse>send(request).toBlocking().single();
        assertTrue(response.status().isSuccess());
        ReferenceCountUtil.release(response);

        Observe.call(
            cluster(),
            bucket(),
            "persInsDoc5",
            0,
            false,
            response.mutationToken(),
            Observe.PersistTo.MASTER,
            Observe.ReplicateTo.ONE,
            Delay.fixed(1, TimeUnit.MILLISECONDS),
            BestEffortRetryStrategy.INSTANCE
        ).toBlocking().single();

    }

