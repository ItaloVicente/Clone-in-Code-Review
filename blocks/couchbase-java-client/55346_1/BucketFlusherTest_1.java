            KeyValueStatus.SUCCESS.code(), 0, BUCKET, Unpooled.EMPTY_BUFFER, null, null);

    private static void assertBuffersFreed(List<ByteBuf> buffers) {
        for (ByteBuf buffer : buffers) {
            assertEquals(0, buffer.refCnt());
        }
    }
