    @SuppressWarnings("unchecked")
    public void shouldParseErrorWithEmptyRows() throws Exception {
        String response = Resources.read("error_empty_rows.json", this.getClass());
        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(200, "OK"));
        HttpContent responseChunk1 = new DefaultLastHttpContent(Unpooled.copiedBuffer(response, CharsetUtil.UTF_8));

        ViewQueryRequest requestMock = mock(ViewQueryRequest.class);
        queue.add(requestMock);
        channel.writeInbound(responseHeader, responseChunk1);
        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        ViewQueryResponse inbound = (ViewQueryResponse) firedEvents.get(0);
        assertTrue(inbound.status().isSuccess());

        List<ByteBuf> rows = inbound.rows().toList().toBlocking().single();
        assertTrue(rows.isEmpty());

        String error = inbound.error().toBlocking().single();
        Map<String, Object> parsed = mapper.readValue(error, Map.class);
        assertEquals(1, parsed.size());
        assertNotNull(parsed.get("errors"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldParseErrorAfterRows() throws Exception {
        String response = Resources.read("error_rows.json", this.getClass());
        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(200, "OK"));
        HttpContent responseChunk1 = new DefaultLastHttpContent(Unpooled.copiedBuffer(response, CharsetUtil.UTF_8));

        ViewQueryRequest requestMock = mock(ViewQueryRequest.class);
        queue.add(requestMock);
        channel.writeInbound(responseHeader, responseChunk1);
        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        ViewQueryResponse inbound = (ViewQueryResponse) firedEvents.get(0);
        assertTrue(inbound.status().isSuccess());

        List<ByteBuf> rows = inbound.rows().toList().toBlocking().single();
        assertEquals(10, rows.size());

        String error = inbound.error().toBlocking().single();
        Map<String, Object> parsed = mapper.readValue(error, Map.class);
        assertEquals(1, parsed.size());
        assertNotNull(parsed.get("errors"));
    }

    @Test
    public void shouldParseErrorWithDesignNotFound() throws Exception {
        String response = Resources.read("designdoc_notfound.json", this.getClass());
        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(404, "Object Not Found"));
        HttpContent responseChunk1 = new DefaultLastHttpContent(Unpooled.copiedBuffer(response, CharsetUtil.UTF_8));
