
    private static void assertBuffersFreed(List<ByteBuf> buffers) {
        for (ByteBuf buffer : buffers) {
            assertEquals(0, buffer.refCnt());
        }
    }
}
