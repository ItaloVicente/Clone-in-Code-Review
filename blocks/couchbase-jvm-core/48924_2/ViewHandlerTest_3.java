
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
        List<Object> parsed = mapper.readValue(error, List.class);
        assertEquals(1, parsed.size());
    }

