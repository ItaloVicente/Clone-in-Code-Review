
    @Test
    public void verifyConstantRetry() throws Exception {
        int count = 20;
        opFailRequest(Long.parseLong("7FF0", 16),  count);
        startRetryVerifyRequest();
        String key = "upsert-key";
        UpsertRequest upsert = new UpsertRequest(key, Unpooled.copiedBuffer("", CharsetUtil.UTF_8), bucket());
        UpsertResponse response = cluster().<UpsertResponse>send(upsert).toBlocking().single();
        ReferenceCountUtil.releaseLater(response.content());
        checkRetryVerifyRequest(Long.parseLong("7FF0", 16), OP_UPSERT, count*5);
        opFailRequest(Long.parseLong("7FF0", 16),  0);
    }

    @Test(expected = Exception.class)
    public void verifyRetryIsStoppedBeforeMaxRetryDuration() throws Exception {
        int count = 100;
        opFailRequest(Long.parseLong("7FF0", 16),  count);
        startRetryVerifyRequest();
        String key = "upsert-key";
        UpsertRequest upsert = new UpsertRequest(key, Unpooled.copiedBuffer("", CharsetUtil.UTF_8), bucket());
        UpsertResponse response = cluster().<UpsertResponse>send(upsert).toBlocking().single();
        ReferenceCountUtil.releaseLater(response.content());
        checkRetryVerifyRequest(Long.parseLong("7FF0", 16), OP_UPSERT, count*5);
        opFailRequest(Long.parseLong("7FF0", 16),  0);
    }

    @Test
    public void verifyLinearRetry() throws Exception {
        int count = 10;
        opFailRequest(Long.parseLong("7FF1", 16), count);
        startRetryVerifyRequest();
        String key = "upsert-key";
        UpsertRequest upsert = new UpsertRequest(key, Unpooled.copiedBuffer("", CharsetUtil.UTF_8), bucket());
        UpsertResponse response = cluster().<UpsertResponse>send(upsert).toBlocking().single();
        ReferenceCountUtil.releaseLater(response.content());
        checkRetryVerifyRequest(Long.parseLong("7FF1", 16), OP_UPSERT, 10);
        opFailRequest(Long.parseLong("7FF1", 16),  0);
    }

    @Test
    public void verifyExponentialRetry() throws Exception {
        int count = 5;
        opFailRequest(Long.parseLong("7FF2", 16), count);
        startRetryVerifyRequest();
        String key = "upsert-key";
        UpsertRequest upsert = new UpsertRequest(key, Unpooled.copiedBuffer("", CharsetUtil.UTF_8), bucket());
        UpsertResponse response = cluster().<UpsertResponse>send(upsert).toBlocking().single();
        ReferenceCountUtil.releaseLater(response.content());
        checkRetryVerifyRequest(Long.parseLong("7FF2", 16), OP_UPSERT, 10);
        opFailRequest(Long.parseLong("7FF2", 16),  0);
    }
