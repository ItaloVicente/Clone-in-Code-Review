    @Test
    public void shouldRetainContentOnStore() throws Exception {
        ByteBuf content = Unpooled.copiedBuffer("foo", CharsetUtil.UTF_8);
        UpsertRequest request = new UpsertRequest("key", content, "bucket");
        request.partition((short) 1);

        assertEquals(1, content.refCnt());
        channel.writeOutbound(request);
        assertEquals(2, content.refCnt());
    }

    @Test
    public void shouldRetainContentOnAppend() throws Exception {
        ByteBuf content = Unpooled.copiedBuffer("foo", CharsetUtil.UTF_8);
        AppendRequest request = new AppendRequest("key", 0, content, "bucket");
        request.partition((short) 1);

        assertEquals(1, content.refCnt());
        channel.writeOutbound(request);
        assertEquals(2, content.refCnt());
    }

    @Test
    public void shouldRetainContentOnPrepend() throws Exception {
        ByteBuf content = Unpooled.copiedBuffer("foo", CharsetUtil.UTF_8);
        PrependRequest request = new PrependRequest("key", 0, content, "bucket");
        request.partition((short) 1);

        assertEquals(1, content.refCnt());
        channel.writeOutbound(request);
        assertEquals(2, content.refCnt());
    }

    @Test
    public void shouldNotRetainContentOnObserve() throws Exception {
        ObserveRequest request = new ObserveRequest("key", 0, false, (short) 1, "bucket");
        request.partition((short) 1);

        channel.writeOutbound(request);
        FullBinaryMemcacheRequest written = (FullBinaryMemcacheRequest) channel.readOutbound();

        assertEquals(1, written.content().refCnt());
    }

    @Test
    public void shouldReleaseStoreRequestContentOnSuccess() throws Exception {
        ByteBuf content = Unpooled.copiedBuffer("content", CharsetUtil.UTF_8);
        FullBinaryMemcacheResponse response = new DefaultFullBinaryMemcacheResponse("key", Unpooled.EMPTY_BUFFER,
            content);
        response.setStatus(BinaryMemcacheResponseStatus.SUCCESS);

        UpsertRequest requestMock = mock(UpsertRequest.class);
        when(requestMock.bucket()).thenReturn("bucket");
        when(requestMock.observable()).thenReturn(AsyncSubject.<CouchbaseResponse>create());
        requestQueue.add(requestMock);

        assertEquals(1, content.refCnt());
        channel.writeInbound(response);
        assertEquals(0, content.refCnt());
    }

    @Test
    public void shouldNotReleaseStoreRequestContentOnRetry() throws Exception {
        ByteBuf content = Unpooled.copiedBuffer("content", CharsetUtil.UTF_8);
        FullBinaryMemcacheResponse response = new DefaultFullBinaryMemcacheResponse("key", Unpooled.EMPTY_BUFFER,
            content);
        response.setStatus(KeyValueHandler.STATUS_NOT_MY_VBUCKET);

        UpsertRequest requestMock = mock(UpsertRequest.class);
        when(requestMock.bucket()).thenReturn("bucket");
        when(requestMock.observable()).thenReturn(AsyncSubject.<CouchbaseResponse>create());
        requestQueue.add(requestMock);

        assertEquals(1, content.refCnt());
        channel.writeInbound(response);
        assertEquals(1, content.refCnt());
    }

    @Test
    public void shouldReleaseAppendRequestContentOnSuccess() throws Exception {
        ByteBuf content = Unpooled.copiedBuffer("content", CharsetUtil.UTF_8);
        FullBinaryMemcacheResponse response = new DefaultFullBinaryMemcacheResponse("key", Unpooled.EMPTY_BUFFER,
            content);
        response.setStatus(BinaryMemcacheResponseStatus.SUCCESS);

        AppendRequest requestMock = mock(AppendRequest.class);
        when(requestMock.bucket()).thenReturn("bucket");
        when(requestMock.observable()).thenReturn(AsyncSubject.<CouchbaseResponse>create());
        requestQueue.add(requestMock);

        assertEquals(1, content.refCnt());
        channel.writeInbound(response);
        assertEquals(0, content.refCnt());
    }

    @Test
    public void shouldNotReleaseAppendRequestContentOnRetry() throws Exception {
        ByteBuf content = Unpooled.copiedBuffer("content", CharsetUtil.UTF_8);
        FullBinaryMemcacheResponse response = new DefaultFullBinaryMemcacheResponse("key", Unpooled.EMPTY_BUFFER,
            content);
        response.setStatus(KeyValueHandler.STATUS_NOT_MY_VBUCKET);

        AppendRequest requestMock = mock(AppendRequest.class);
        when(requestMock.bucket()).thenReturn("bucket");
        when(requestMock.observable()).thenReturn(AsyncSubject.<CouchbaseResponse>create());
        requestQueue.add(requestMock);

        assertEquals(1, content.refCnt());
        channel.writeInbound(response);
        assertEquals(1, content.refCnt());
    }

    @Test
    public void shouldReleasePrependRequestContentOnSuccess() throws Exception {
        ByteBuf content = Unpooled.copiedBuffer("content", CharsetUtil.UTF_8);
        FullBinaryMemcacheResponse response = new DefaultFullBinaryMemcacheResponse("key", Unpooled.EMPTY_BUFFER,
            content);
        response.setStatus(BinaryMemcacheResponseStatus.SUCCESS);

        PrependRequest requestMock = mock(PrependRequest.class);
        when(requestMock.bucket()).thenReturn("bucket");
        when(requestMock.observable()).thenReturn(AsyncSubject.<CouchbaseResponse>create());
        requestQueue.add(requestMock);

        assertEquals(1, content.refCnt());
        channel.writeInbound(response);
        assertEquals(0, content.refCnt());
    }

    @Test
    public void shouldNotReleasePrependRequestContentOnRetry() throws Exception {
        ByteBuf content = Unpooled.copiedBuffer("content", CharsetUtil.UTF_8);
        FullBinaryMemcacheResponse response = new DefaultFullBinaryMemcacheResponse("key", Unpooled.EMPTY_BUFFER,
            content);
        response.setStatus(KeyValueHandler.STATUS_NOT_MY_VBUCKET);

        PrependRequest requestMock = mock(PrependRequest.class);
        when(requestMock.bucket()).thenReturn("bucket");
        when(requestMock.observable()).thenReturn(AsyncSubject.<CouchbaseResponse>create());
        requestQueue.add(requestMock);

        assertEquals(1, content.refCnt());
        channel.writeInbound(response);
        assertEquals(1, content.refCnt());
    }
