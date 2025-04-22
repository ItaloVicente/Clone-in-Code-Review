    @Test
    public void shouldHandleSpecialKeyChars() {
        String key = "AVERY® READY INDEX®";
        String content = "Hello World!";
        UpsertRequest upsert = new UpsertRequest(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), bucket());
        UpsertResponse response = cluster().<UpsertResponse>send(upsert).toBlocking().single();
        ReferenceCountUtil.releaseLater(response.content());

        GetRequest request = new GetRequest(key, bucket());
        GetResponse getResponse = cluster().<GetResponse>send(request).toBlocking().single();
        assertEquals(content, getResponse.content().toString(CharsetUtil.UTF_8));
        ReferenceCountUtil.releaseLater(getResponse.content());
    }

