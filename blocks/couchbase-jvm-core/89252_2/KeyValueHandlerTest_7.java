
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

