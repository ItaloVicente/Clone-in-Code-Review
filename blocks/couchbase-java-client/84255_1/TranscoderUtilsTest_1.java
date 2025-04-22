    @Test
    public void shouldDecodeToClassFromDirectBuffer() throws Exception {
        ByteBuf input = Unpooled.directBuffer();
        input.writeBytes("{\"hello\": \"world\", \"direct\": true}".getBytes(CharsetUtil.UTF_8));

        JsonObject result = TranscoderUtils.byteBufToClass(input, JsonObject.class, JacksonTransformers.MAPPER);
        assertEquals(JsonObject.create().put("hello", "world").put("direct", true), result);
        assertEquals(input.refCnt(), 1);
        input.release();
    }

    @Test
    public void shouldDecodeToClassFromHeapBuffer() throws Exception {
        ByteBuf input = Unpooled.buffer();
        input.writeBytes("{\"hello\": \"world\", \"direct\": true}".getBytes(CharsetUtil.UTF_8));

        JsonObject result = TranscoderUtils.byteBufToClass(input, JsonObject.class, JacksonTransformers.MAPPER);
        assertEquals(JsonObject.create().put("hello", "world").put("direct", true), result);
        assertEquals(input.refCnt(), 1);
        input.release();
    }

