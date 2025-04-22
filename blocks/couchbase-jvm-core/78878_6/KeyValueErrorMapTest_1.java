
    @Test
    public void verifyConstantRetry() throws Exception {
        opFailRequest(Long.parseLong("7FF0", 16), 30);
        startRetryVerifyRequest();
        String key = "upsert-key";
        UpsertRequest upsert = new UpsertRequest(key, Unpooled.copiedBuffer("", CharsetUtil.UTF_8), bucket());
        UpsertResponse response = cluster().<UpsertResponse>send(upsert).toBlocking().single();
        ReferenceCountUtil.releaseLater(response.content());
        checkRetryVerifyRequest(Long.parseLong("7FF0", 16), OP_UPSERT);
    }

    @Test
    public void verifyLinearRetry() throws Exception {
        opFailRequest(Long.parseLong("7FF1", 16), 10);
        startRetryVerifyRequest();
        String key = "upsert-key";
        UpsertRequest upsert = new UpsertRequest(key, Unpooled.copiedBuffer("", CharsetUtil.UTF_8), bucket());
        UpsertResponse response = cluster().<UpsertResponse>send(upsert).toBlocking().single();
        ReferenceCountUtil.releaseLater(response.content());
        checkRetryVerifyRequest(Long.parseLong("7FF1", 16), OP_UPSERT);
    }

    @Test
    public void verifyExponentialRetry() throws Exception {
        opFailRequest(Long.parseLong("7FF2", 16), 5);
        startRetryVerifyRequest();
        String key = "upsert-key";
        UpsertRequest upsert = new UpsertRequest(key, Unpooled.copiedBuffer("", CharsetUtil.UTF_8), bucket());
        UpsertResponse response = cluster().<UpsertResponse>send(upsert).toBlocking().single();
        ReferenceCountUtil.releaseLater(response.content());
        checkRetryVerifyRequest(Long.parseLong("7FF2", 16), OP_UPSERT);
    }
