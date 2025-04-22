    @Test
    public void testFindNextCharNotPrefixedByAfterReaderIndexMoved() throws Exception {
        ByteBuf source = Unpooled.copiedBuffer("sectionWithLotsAndLotsOfDataToSkip\": \"123456\\\"78901234567890\"",
                CharsetUtil.UTF_8);
        source.skipBytes(38);

        assertEquals(22, ByteBufJsonHelper.findNextCharNotPrefixedBy(source, '"', '\\'));
    }

