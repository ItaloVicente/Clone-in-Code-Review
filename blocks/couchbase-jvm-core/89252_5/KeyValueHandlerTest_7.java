
    @Test
    public void shouldDecodeEmptyTracingDurationFrame() {
        ByteBuf input = Unpooled.buffer();
        assertEquals(0, KeyValueHandler.parseServerDurationFromFrame(input));
        assertEquals(0, input.readableBytes());
    }

    @Test
    public void shouldDecodeZeroTracingDurationFrame() {
        ByteBuf input = Unpooled.buffer()
            .writeByte(2) // id: 0, len: 2
            .writeShort(0);
        assertEquals(0, KeyValueHandler.parseServerDurationFromFrame(input));
        assertEquals(0, input.readableBytes());
    }

    @Test
    public void shouldDecodeMaxTracingDurationFrame() {
        ByteBuf input = Unpooled.buffer()
            .writeByte(2) // id: 0, len: 2
            .writeShort(65535);
        assertEquals(120125042, KeyValueHandler.parseServerDurationFromFrame(input));
        assertEquals(0, input.readableBytes());
    }

    @Test
    public void shouldDecodeNormalTracingDurationFrame() {
        ByteBuf input = Unpooled.buffer()
            .writeByte(2) // id: 0, len: 2
            .writeShort(1234);
        assertEquals(119635, KeyValueHandler.parseServerDurationFromFrame(input));
        assertEquals(0, input.readableBytes());
    }

    @Test
    public void shouldSkipDifferentFrame() {
        ByteBuf input = Unpooled.buffer()
            .writeByte(0x12) // id: 1, len: 2
            .writeShort(0)
            .writeByte(0x02) // id: 0, len: 2
            .writeShort(1234);
        assertEquals(119635, KeyValueHandler.parseServerDurationFromFrame(input));
        assertEquals(0, input.readableBytes());
    }

    @Test
    public void shouldDecodeResponseGetWithTracingData() {
        ByteBuf content = Unpooled.copiedBuffer("content", CharsetUtil.UTF_8);
        FullBinaryMemcacheResponse response = new DefaultFullBinaryMemcacheResponse(KEY, Unpooled.EMPTY_BUFFER,
            content.copy());
        response.setCAS(123456789L);
        response.setExtras(Unpooled.buffer().writeInt(123));
        response.setExtrasLength((byte) 4);
        response.setFramingExtras(Unpooled.buffer().writeByte(0x02).writeShort(1234));
        response.setFramingExtrasLength((byte) 3);

        GetRequest requestMock = mock(GetRequest.class);
        when(requestMock.bucket()).thenReturn(BUCKET);
        requestQueue.add(requestMock);
        channel.writeInbound(response);

        assertEquals(1, eventSink.responseEvents().size());
        GetResponse event = (GetResponse) eventSink.responseEvents().get(0).getMessage();
        assertEquals(123456789L, event.cas());
        assertEquals(123, event.flags());
        assertEquals("content", event.content().toString(CHARSET));
        assertEquals(119635, event.serverDuration());
        assertEquals(BUCKET, event.bucket());
    }

