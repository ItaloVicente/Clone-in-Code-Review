    @Test
    public void shouldGetAndLockWithAppend() {
        String key = "get-and-lock-append";

        UpsertRequest request = new UpsertRequest(key, Unpooled.copiedBuffer("foo", CharsetUtil.UTF_8), bucket());
        UpsertResponse response = cluster().<UpsertResponse>send(request).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, response.status());
        ReferenceCountUtil.releaseLater(response.content());

        GetResponse getResponse = cluster().<GetResponse>send(new GetRequest(key, bucket(), true, false, 2)).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, getResponse.status());
        assertEquals("foo", getResponse.content().toString(CharsetUtil.UTF_8));
        ReferenceCountUtil.releaseLater(getResponse.content());

        AppendResponse appendResponse = cluster().<AppendResponse>send(
            new AppendRequest(key, getResponse.cas(), Unpooled.copiedBuffer("bar", CharsetUtil.UTF_8), bucket())
        ).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, getResponse.status());
        assertTrue(getResponse.cas() != appendResponse.cas());
        ReferenceCountUtil.releaseLater(appendResponse.content());

        getResponse = cluster().<GetResponse>send(new GetRequest(key, bucket(), false, false, 0)).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, getResponse.status());
        assertEquals("foobar", getResponse.content().toString(CharsetUtil.UTF_8));
        ReferenceCountUtil.releaseLater(getResponse.content());
    }

