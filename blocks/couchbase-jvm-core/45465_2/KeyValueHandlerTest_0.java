    @Test(expected = CouchbaseException.class)
    public void shouldFailWhenOpaqueDoesNotMatch() throws Exception {
        ByteBuf content = Unpooled.copiedBuffer("content", CharsetUtil.UTF_8);
        FullBinaryMemcacheResponse response = new DefaultFullBinaryMemcacheResponse("key", Unpooled.EMPTY_BUFFER,
                content);
        response.setStatus(BinaryMemcacheResponseStatus.SUCCESS);
        response.setOpaque(1);

        PrependRequest requestMock = mock(PrependRequest.class);
        ByteBuf requestContent = Unpooled.copiedBuffer("content", CharsetUtil.UTF_8);
        when(requestMock.bucket()).thenReturn("bucket");
        AsyncSubject<CouchbaseResponse> responseSubject = AsyncSubject.<CouchbaseResponse>create();
        when(requestMock.observable()).thenReturn(responseSubject);
        when(requestMock.content()).thenReturn(requestContent);
        when(requestMock.opaque()).thenReturn(3);
        requestQueue.add(requestMock);

        channel.writeInbound(response);
        assertEquals(0, content.refCnt());
        responseSubject.toBlocking().single();
