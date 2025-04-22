    private ByteBuf tryCompress(ByteBuf buf) {
        final int uncompressedLength = buf.readableBytes();
        final byte[] compressed = new byte[Snappy.maxCompressedLength(uncompressedLength)];
        final int compressedLength;

        if (buf.hasArray()) {
            compressedLength = Snappy.compress(
                    buf.array(), buf.arrayOffset() + buf.readerIndex(), uncompressedLength, compressed, 0);
        } else {
            final byte[] data = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), data);
            compressedLength = Snappy.compress(data, 0, data.length, compressed, 0);
        }

        final double ratio = (double) compressedLength / uncompressedLength;
        if (ratio > minCompressionRatio) {
            return null;
        }

        final byte[] trimmedCompressed = Arrays.copyOf(compressed, compressedLength);
        return Unpooled.wrappedBuffer(trimmedCompressed);
    }

