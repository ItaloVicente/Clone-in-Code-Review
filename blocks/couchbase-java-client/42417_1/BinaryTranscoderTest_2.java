    @Test
    public void shouldNotReleaseBufferWhenDecoded() {
        ByteBuf content = ReferenceCountUtil.releaseLater(Unpooled.copiedBuffer("value", CharsetUtil.UTF_8));
        converter.decode("id", content, 0, 0, TranscoderUtils.BINARY_COMMON_FLAGS,
            ResponseStatus.SUCCESS);
        assertEquals(1, content.refCnt());
    }

    @Test(expected = TranscodingException.class)
    public void shouldReleaseBufferWhenError() {
        ByteBuf content = Unpooled.copiedBuffer("[]", CharsetUtil.UTF_8);
        int wrongFlags = 1234;
        try {
            converter.decode("id", content, 0, 0, wrongFlags,
                ResponseStatus.SUCCESS);
        } finally {
            assertEquals(0, content.refCnt());
        }
    }

