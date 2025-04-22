
    @Test
    public void shouldIgnoreJsonStringWithRandomSectionChars() {
        ByteBuf source = Unpooled.copiedBuffer(
                "{ this is \"a string \\\"with escaped quote and sectionChars like } or {{{!\" }", CharsetUtil.UTF_8);

        int closingPos = source.forEachByte(new ClosingPositionBufProcessor('{', '}', true));

        assertEquals(74, closingPos);
        assertEquals(0, source.readerIndex());
    }

    @Test
    public void shouldIgnoreJsonStringWithClosingSectionCharEvenIfStreamInterrupted() {
        ByteBuf source = Unpooled.copiedBuffer(
                "{ this is \"a string \\\"with }", CharsetUtil.UTF_8);

        int closingPos = source.forEachByte(new ClosingPositionBufProcessor('{', '}', true));

        assertEquals(-1, closingPos);
        assertEquals(0, source.readerIndex());
    }
