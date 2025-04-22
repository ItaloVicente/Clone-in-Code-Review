    @Test
    public void shouldGetAndTouch() throws Exception {
        String key = "get-and-touch";

        UpsertRequest request = new UpsertRequest(key, Unpooled.copiedBuffer("content", CharsetUtil.UTF_8), 3, 0, bucket());
        UpsertResponse response = cluster().<UpsertResponse>send(request).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, response.status());

        Thread.sleep(2000);

        GetResponse getResponse = cluster().<GetResponse>send(new GetRequest(key, bucket(), false, true, 3)).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, response.status());
        assertEquals("content", getResponse.content().toString(CharsetUtil.UTF_8));

        Thread.sleep(2000);

        getResponse = cluster().<GetResponse>send(new GetRequest(key, bucket(), false, true, 3)).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, response.status());
        assertEquals("content", getResponse.content().toString(CharsetUtil.UTF_8));
    }

    @Test
    public void shouldGetAndLock() throws Exception {
        String key = "get-and-lock";

        UpsertRequest request = new UpsertRequest(key, Unpooled.copiedBuffer("content", CharsetUtil.UTF_8), bucket());
        UpsertResponse response = cluster().<UpsertResponse>send(request).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, response.status());

        GetResponse getResponse = cluster().<GetResponse>send(new GetRequest(key, bucket(), true, false, 2)).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, response.status());
        assertEquals("content", getResponse.content().toString(CharsetUtil.UTF_8));

        request = new UpsertRequest(key, Unpooled.copiedBuffer("content", CharsetUtil.UTF_8), bucket());
        response = cluster().<UpsertResponse>send(request).toBlocking().single();
        assertEquals(ResponseStatus.EXISTS, response.status());

        Thread.sleep(3000);

        request = new UpsertRequest(key, Unpooled.copiedBuffer("content", CharsetUtil.UTF_8), bucket());
        response = cluster().<UpsertResponse>send(request).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, response.status());
    }

