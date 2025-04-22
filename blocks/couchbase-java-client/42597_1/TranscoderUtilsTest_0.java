    @Test
    public void testHasBinaryFlags() {
        assertTrue(TranscoderUtils.hasBinaryFlags(TranscoderUtils.BINARY_COMMON_FLAGS));
        assertTrue(TranscoderUtils.hasBinaryFlags(TranscoderUtils.BINARY_COMPAT_FLAGS));
        assertTrue(TranscoderUtils.hasBinaryFlags((8 << 8)));
    }

