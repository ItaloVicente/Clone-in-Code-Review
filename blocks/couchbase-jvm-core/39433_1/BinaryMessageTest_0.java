    @Test
    public void shouldTouch() throws Exception {
        String key = "touch";

        UpsertRequest request = new UpsertRequest(key, Unpooled.copiedBuffer("content", CharsetUtil.UTF_8), 3, 0, bucket());
        UpsertResponse response = cluster().<UpsertResponse>send(request).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, response.status());

        Thread.sleep(2000);

        TouchResponse touchResponse = cluster().<TouchResponse>send(new TouchRequest(key, 3, bucket())).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, touchResponse.status());

        Thread.sleep(2000);

        GetResponse getResponse = cluster().<GetResponse>send(new GetRequest(key, bucket())).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, response.status());
        assertEquals("content", getResponse.content().toString(CharsetUtil.UTF_8));
    }

    @Test
    public void shouldUnlock() throws Exception {
        String key = "unlock";

        UpsertRequest request = new UpsertRequest(key, Unpooled.copiedBuffer("content", CharsetUtil.UTF_8), bucket());
        UpsertResponse response = cluster().<UpsertResponse>send(request).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, response.status());

        GetResponse getResponse = cluster().<GetResponse>send(new GetRequest(key, bucket(), true, false, 15)).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, response.status());
        assertEquals("content", getResponse.content().toString(CharsetUtil.UTF_8));

        request = new UpsertRequest(key, Unpooled.copiedBuffer("content", CharsetUtil.UTF_8), bucket());
        response = cluster().<UpsertResponse>send(request).toBlocking().single();
        assertEquals(ResponseStatus.EXISTS, response.status());

        UnlockRequest unlockRequest = new UnlockRequest(key, getResponse.cas(), bucket());
        UnlockResponse unlockResponse = cluster().<UnlockResponse>send(unlockRequest).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, unlockResponse.status());

        request = new UpsertRequest(key, Unpooled.copiedBuffer("content", CharsetUtil.UTF_8), bucket());
        response = cluster().<UpsertResponse>send(request).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, response.status());
    }

