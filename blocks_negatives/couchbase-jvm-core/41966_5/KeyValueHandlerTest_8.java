    @Test
    public void shouldDecodeGetSuccessResponse() throws Exception {
        ByteBuf content = Unpooled.copiedBuffer("content", CharsetUtil.UTF_8);
        FullBinaryMemcacheResponse response = new DefaultFullBinaryMemcacheResponse("key", Unpooled.EMPTY_BUFFER,
            content.copy());
        response.setCAS(123456789L);
        response.setExtras(Unpooled.buffer().writeInt(123));
        response.setExtrasLength((byte) 4);

        GetRequest requestMock = mock(GetRequest.class);
        when(requestMock.bucket()).thenReturn("bucket");
        queue.add(requestMock);
        channel.writeInbound(response);
        channel.readInbound();

        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        GetResponse inbound = (GetResponse) firedEvents.get(0);
        assertEquals(ResponseStatus.SUCCESS, inbound.status());
        assertEquals("bucket", inbound.bucket());
        assertEquals(123456789L, inbound.cas());
        assertEquals(123, inbound.flags());
        assertEquals("content", inbound.content().toString(CharsetUtil.UTF_8));
        assertTrue(queue.isEmpty());

        ReferenceCountUtil.release(content);
    }

    @Test
    public void shouldDecodeGetNotFoundResponse() throws Exception {
        ByteBuf content = Unpooled.copiedBuffer("Not Found", CharsetUtil.UTF_8);
        FullBinaryMemcacheResponse response = new DefaultFullBinaryMemcacheResponse("key", Unpooled.EMPTY_BUFFER,
            content.copy());
        response.setStatus(BinaryMemcacheResponseStatus.KEY_ENOENT);

        GetRequest requestMock = mock(GetRequest.class);
        when(requestMock.bucket()).thenReturn("bucket");
        queue.add(requestMock);
        channel.writeInbound(response);
        channel.readInbound();

        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        GetResponse inbound = (GetResponse) firedEvents.get(0);
        assertEquals(ResponseStatus.NOT_EXISTS, inbound.status());
        assertEquals("bucket", inbound.bucket());
        assertEquals(0, inbound.cas());
        assertEquals(0, inbound.flags());
        assertEquals("Not Found", inbound.content().toString(CharsetUtil.UTF_8));
        assertTrue(queue.isEmpty());

        ReferenceCountUtil.release(content);
    }

    @Test
    public void shouldDecodeReplicaGetResponse() throws Exception {
        ByteBuf content = Unpooled.copiedBuffer("content", CharsetUtil.UTF_8);
        FullBinaryMemcacheResponse response = new DefaultFullBinaryMemcacheResponse("key", Unpooled.EMPTY_BUFFER,
            content.copy());
        response.setCAS(123456789L);
        response.setExtras(Unpooled.buffer().writeInt(123));
        response.setExtrasLength((byte) 4);

        ReplicaGetRequest requestMock = mock(ReplicaGetRequest.class);
        when(requestMock.bucket()).thenReturn("bucket");
        queue.add(requestMock);
        channel.writeInbound(response);
        channel.readInbound();

        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        GetResponse inbound = (GetResponse) firedEvents.get(0);
        assertEquals(ResponseStatus.SUCCESS, inbound.status());
        assertEquals("bucket", inbound.bucket());
        assertEquals(123456789L, inbound.cas());
        assertEquals(123, inbound.flags());
        assertEquals("content", inbound.content().toString(CharsetUtil.UTF_8));
        assertTrue(queue.isEmpty());

        ReferenceCountUtil.release(content);
    }

