    @Test
    public void shouldDecodeSuccessfulGet() {
        ByteBuf content = Unpooled.copiedBuffer("content", CharsetUtil.UTF_8);
        FullBinaryMemcacheResponse response = new DefaultFullBinaryMemcacheResponse("key", Unpooled.EMPTY_BUFFER,
            content.copy());
        response.setCAS(123456789L);
        response.setExtras(Unpooled.buffer().writeInt(123));
        response.setExtrasLength((byte) 4);

        GetRequest requestMock = mock(GetRequest.class);
        when(requestMock.bucket()).thenReturn(BUCKET);
        requestQueue.add(requestMock);
        channel.writeInbound(response);

        assertEquals(1, eventSink.responseEvents().size());
        GetResponse event = (GetResponse) eventSink.responseEvents().get(0).getMessage();
        assertEquals(123456789L, event.cas());
        assertEquals(123, event.flags());
        assertEquals("content", event.content().toString(CHARSET));
        assertEquals(BUCKET, event.bucket());
    }

    @Test
    public void shouldDecodeNotFoundGet() {
        ByteBuf content = Unpooled.copiedBuffer("Not Found", CharsetUtil.UTF_8);
        FullBinaryMemcacheResponse response = new DefaultFullBinaryMemcacheResponse("key", Unpooled.EMPTY_BUFFER,
            content.copy());
        response.setStatus(BinaryMemcacheResponseStatus.KEY_ENOENT);

        GetRequest requestMock = mock(GetRequest.class);
        when(requestMock.bucket()).thenReturn(BUCKET);
        requestQueue.add(requestMock);
        channel.writeInbound(response);

        assertEquals(1, eventSink.responseEvents().size());
        GetResponse event = (GetResponse) eventSink.responseEvents().get(0).getMessage();
        assertEquals(0, event.cas());
        assertEquals(0, event.flags());
        assertEquals("Not Found", event.content().toString(CHARSET));
        assertEquals(BUCKET, event.bucket());
    }

    @Test
    public void shouldEncodeReplicaGetRequest() {
        String id = "replicakey";
        ReplicaGetRequest request = new ReplicaGetRequest(id, BUCKET, (short) 1);
        request.partition((short) 512);

        channel.writeOutbound(request);
        BinaryMemcacheRequest outbound = (BinaryMemcacheRequest) channel.readOutbound();
        assertNotNull(outbound);
        assertEquals(id, outbound.getKey());
        assertEquals(id.length(), outbound.getKeyLength());
        assertEquals(id.length(), outbound.getTotalBodyLength());
        assertEquals(512, outbound.getReserved());
        assertEquals(KeyValueHandler.OP_GET_REPLICA, outbound.getOpcode());
        assertEquals(0, outbound.getExtrasLength());
    }

    @Test
    public void shouldDecodeReplicaGetResponse() {
        ByteBuf content = Unpooled.copiedBuffer("content", CharsetUtil.UTF_8);
        FullBinaryMemcacheResponse response = new DefaultFullBinaryMemcacheResponse("key", Unpooled.EMPTY_BUFFER,
            content.copy());
        response.setCAS(123456789L);
        response.setExtras(Unpooled.buffer().writeInt(123));
        response.setExtrasLength((byte) 4);

        ReplicaGetRequest requestMock = mock(ReplicaGetRequest.class);
        when(requestMock.bucket()).thenReturn(BUCKET);
        requestQueue.add(requestMock);
        channel.writeInbound(response);

        assertEquals(1, eventSink.responseEvents().size());
        GetResponse event = (GetResponse) eventSink.responseEvents().get(0).getMessage();
        assertEquals(123456789L, event.cas());
        assertEquals(123, event.flags());
        assertEquals("content", event.content().toString(CHARSET));
        assertEquals(BUCKET, event.bucket());
    }


