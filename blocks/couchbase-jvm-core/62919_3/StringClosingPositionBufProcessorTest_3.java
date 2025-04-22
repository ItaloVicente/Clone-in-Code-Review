
    @Test
    public void testClosingPosFoundInStringWithEscapedBackslashLast() {
        ByteBuf source = Unpooled.copiedBuffer("\"abc\\\\\"", CharsetUtil.UTF_8);
        int closingPos = source.forEachByte(new StringClosingPositionBufProcessor());

        assertEquals(6, closingPos);
        assertEquals(0, source.readerIndex());
    }
