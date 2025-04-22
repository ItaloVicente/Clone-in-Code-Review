
    @Test
    public void shouldReleaseBufferWhenDecoded() {
        ByteBuf content = Unpooled.copiedBuffer("9223372036854775807", CharsetUtil.UTF_8);
        JsonLongDocument decoded = converter.decode("id", content, 0, 0, TranscoderUtils.JSON_COMPAT_FLAGS,
            ResponseStatus.SUCCESS);
        assertEquals(0, content.refCnt());
    }

    @Test(expected = TranscodingException.class)
    public void shouldReleaseBufferWhenError() {
        ByteBuf content = Unpooled.copiedBuffer("9223372036854775807", CharsetUtil.UTF_8);
        int wrongFlags = 1234;
        try {
            converter.decode("id", content, 0, 0, wrongFlags, ResponseStatus.SUCCESS);
        } finally {
            assertEquals(0, content.refCnt());
        }
    }
