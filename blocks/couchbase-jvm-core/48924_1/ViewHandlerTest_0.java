    @Ignore("TODO")
    public void shouldParseErrorWithEmptyRows() throws Exception {
        String response = Resources.read("error_empty_rows.json", this.getClass());
        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(200, "OK"));
        HttpContent responseChunk1 = new DefaultLastHttpContent(Unpooled.copiedBuffer(response.substring(1234), CharsetUtil.UTF_8));

        ViewQueryRequest requestMock = mock(ViewQueryRequest.class);
        queue.add(requestMock);
        channel.writeInbound(responseHeader, responseChunk1);
        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        ViewQueryResponse inbound = (ViewQueryResponse) firedEvents.get(0);
    }

    @Test
    @Ignore("TODO")

    public void shouldParseErrorAfterRows() {
        assertTrue(false);

    }

    @Test
    @Ignore("TODO")
