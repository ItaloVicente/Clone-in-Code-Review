
    @Test
    public void verifyConstantRetry() throws Exception {
        opFailRequest(Long.parseLong("7FF0", 16),  -1);
        startRetryVerifyRequest();
        try {
            String key = "upsert-key";
            UpsertRequest upsert = new UpsertRequest(key, Unpooled.copiedBuffer("", CharsetUtil.UTF_8), bucket());
            UpsertResponse response = cluster().<UpsertResponse>send(upsert).toBlocking().single();
            ReferenceCountUtil.releaseLater(response.content());
        } catch (Exception ex) {
        }
        checkRetryVerifyRequest(Long.parseLong("7FF0", 16), OP_UPSERT, 30);
        opFailRequest(Long.parseLong("7FF0", 16),  0);
    }

    @Test
    public void verifyLinearRetry() throws Exception {
        opFailRequest(Long.parseLong("7FF1", 16), -1);
        startRetryVerifyRequest();
        try {
            String key = "upsert-key";
            UpsertRequest upsert = new UpsertRequest(key, Unpooled.copiedBuffer("", CharsetUtil.UTF_8), bucket());
            UpsertResponse response = cluster().<UpsertResponse>send(upsert).toBlocking().single();
            ReferenceCountUtil.releaseLater(response.content());
        } catch (Exception ex) {
        }
        checkRetryVerifyRequest(Long.parseLong("7FF1", 16), OP_UPSERT, 10);
        opFailRequest(Long.parseLong("7FF1", 16),  0);
    }

    @Test
    public void verifyExponentialRetry() throws Exception {
        opFailRequest(Long.parseLong("7FF2", 16), -1);
        startRetryVerifyRequest();
        try {
            String key = "upsert-key";
            UpsertRequest upsert = new UpsertRequest(key, Unpooled.copiedBuffer("", CharsetUtil.UTF_8), bucket());
            UpsertResponse response = cluster().<UpsertResponse>send(upsert).toBlocking().single();
            ReferenceCountUtil.releaseLater(response.content());
        } catch (Exception ex) {
        }
        checkRetryVerifyRequest(Long.parseLong("7FF2", 16), OP_UPSERT, 10);
        opFailRequest(Long.parseLong("7FF2", 16),  0);
    }
