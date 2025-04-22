    @Test
    public void testFindNextCharNotPrefixedByOnRealLifeExample() throws Exception {
        ByteBuf source = Unpooled.copiedBuffer("123456\\\"78901234567890\",\n  \"signature\": ", CharsetUtil.UTF_8);

        assertEquals(22, ByteBufJsonHelper.findNextCharNotPrefixedBy(source, '"', '\\'));
    }

