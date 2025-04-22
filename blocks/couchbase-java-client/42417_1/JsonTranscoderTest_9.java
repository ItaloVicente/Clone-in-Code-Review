
    @Test
    public void shouldReleaseBufferWhenDecoded() {
        ByteBuf content = Unpooled.copiedBuffer("{}", CharsetUtil.UTF_8);
        JsonDocument decoded = converter.decode("id", content, 0, 0, TranscoderUtils.JSON_COMMON_FLAGS, ResponseStatus.SUCCESS);

        assertEquals(0, content.refCnt());
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

    @Test
    public void shouldNotReleaseBufferWhenBufToJson() throws Exception {
        ByteBuf content = ReferenceCountUtil.releaseLater(
            Unpooled.copiedBuffer("{}", CharsetUtil.UTF_8));
        JsonObject decoded = converter.byteBufToJsonObject(content);
        assertEquals(1, content.refCnt());

        content = ReferenceCountUtil.releaseLater(
            Unpooled.copiedBuffer("thisIsNotJson", CharsetUtil.UTF_8));
        try {
            decoded = converter.byteBufToJsonObject(content);
            fail();
        } catch (JsonParseException e) {
        } catch (Exception e) {
            fail(e.toString());
        } finally {
            assertEquals(1, content.refCnt());
        }
    }
