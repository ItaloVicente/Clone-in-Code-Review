    @Test
    public void encoderCandidateIndexesAreUnsigned() throws Exception {
        ByteBuf original = Unpooled.buffer();
        ByteBuf compressed = null;
        try {
            original.writeZero(0x8000);
            for (int i = 0; i < 100; i++) {
                original.writeByte(1);
            }
            compressed = compressAndVerify(original);
        } finally {
            release(original);
            release(compressed);
        }
    }

    @Test
    public void encoderEmitsUnsignedCopyOffsets() throws Exception {
        ByteBuf original = Unpooled.buffer();
        ByteBuf compressed = Unpooled.buffer();

        try {
            original.writeZero(64);
            for (int i = 0; i < 0x8000; i++) {
                original.writeByte(0xff); // hurdle for the copy operation to jump over
            }
            original.writeZero(5); // 5 is enough to trigger a copy instead of a literal (with current encoder impl)

            compressed = compressAndVerify(original);

            compressed.readerIndex(compressed.writerIndex() - 3);
            assertEquals(0x02, compressed.readByte() & 0x03); // a 2-byte copy (ignore length)
            int copyOffset = compressed.order(ByteOrder.LITTLE_ENDIAN).readUnsignedShort();
            assertTrue(copyOffset >= 0x8000);

        } finally {
            release(original);
            release(compressed);
        }
    }

    private static ByteBuf compressAndVerify(ByteBuf original) {
        ByteBuf compressed = Unpooled.buffer();
        ByteBuf decompressed = Unpooled.buffer();

        try {
            new Snappy().encode(original, compressed, original.readableBytes());
            new Snappy().decode(compressed, decompressed);

            assertEquals(original.readerIndex(0), decompressed);
            return compressed;
        } finally {
            release(decompressed);
        }
    }

