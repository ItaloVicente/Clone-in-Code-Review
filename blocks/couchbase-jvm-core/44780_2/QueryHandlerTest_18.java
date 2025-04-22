    @Test
    public void shouldDecodeOneRowResponseWithQuotesInClientIdAndResults() throws Exception {
        String response = Resources.read("withEscapedQuotes.json", this.getClass());
        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(200, "OK"));
        HttpContent responseChunk = new DefaultLastHttpContent(Unpooled.copiedBuffer(response, CharsetUtil.UTF_8));

        GenericQueryRequest requestMock = mock(GenericQueryRequest.class);
        queue.add(requestMock);
        channel.writeInbound(responseHeader, responseChunk);
        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        GenericQueryResponse inbound = (GenericQueryResponse) firedEvents.get(0);
