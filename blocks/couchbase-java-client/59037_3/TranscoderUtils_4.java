    public static class ByteBufToArray {
        public final byte[] byteArray;
        public final int offset;
        public final int length;

        public ByteBufToArray(byte[] byteArray, int offset, int length) {
            this.byteArray = byteArray;
            this.offset = offset;
            this.length = length;
        }
    }

    public static ByteBufToArray byteBufToByteArray(ByteBuf input) {
        byte[] inputBytes;
        int offset = 0;
        int length = input.readableBytes();
        if (input.hasArray()) {
            inputBytes = input.array();
            offset = input.arrayOffset() + input.readerIndex();
        } else {
            inputBytes = new byte[length];
            input.getBytes(input.readerIndex(), inputBytes);
        }
        return new ByteBufToArray(inputBytes, offset, length);
    }

