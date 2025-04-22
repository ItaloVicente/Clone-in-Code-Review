    @Test
    public void shouldReleaseBufferWhenDecoded() {
        ByteBuf content = Unpooled.copiedBuffer("1.7976931348623157E308", CharsetUtil.UTF_8);
        JsonDoubleDocument decoded = converter.decode("id", content, 0, 0, TranscoderUtils.JSON_COMMON_FLAGS,
            ResponseStatus.SUCCESS);
        assertEquals(0, content.refCnt());
    }

    @Test(expected = TranscodingException.class)
    public void shouldReleaseBufferWhenError() {
        ByteBuf content = Unpooled.copiedBuffer("1.7976931348623157E308", CharsetUtil.UTF_8);
        int wrongFlags = 1234;
        try {
            converter.decode("id", content, 0, 0, wrongFlags, ResponseStatus.SUCCESS);
        } finally {
            assertEquals(0, content.refCnt());
        }
    }
