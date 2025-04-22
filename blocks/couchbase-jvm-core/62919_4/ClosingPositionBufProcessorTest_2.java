    @Test
    public void shouldSkipStringWithEscapedBackslashJustBeforeClosingQuote() {
        ByteBuf source = Unpooled.copiedBuffer("{\"some\": \"weird }object\\\\\"}", CharsetUtil.UTF_8);
        int closingPos = source.forEachByte(new ClosingPositionBufProcessor('{', '}', true));

        assertEquals(26, closingPos);
        assertEquals(0, source.readerIndex());
    }

    @Test
    public void shouldSkipEmptyString() {
        ByteBuf source = Unpooled.copiedBuffer("{\"some\": \"\"}", CharsetUtil.UTF_8);
        int closingPos = source.forEachByte(new ClosingPositionBufProcessor('{', '}', true));

        assertEquals(11, closingPos);
        assertEquals(0, source.readerIndex());
    }

    @Test
    public void shouldSkipStringWithEscapedBackslashOnly() {
        ByteBuf source = Unpooled.copiedBuffer("{\"some\": \"\\\\\"}", CharsetUtil.UTF_8);
        int closingPos = source.forEachByte(new ClosingPositionBufProcessor('{', '}', true));

        assertEquals(13, closingPos);
        assertEquals(0, source.readerIndex());
    }

