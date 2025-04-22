    
    public static byte[] copyByteBufToByteArray(ByteBuf input) {
        byte[] copy;
        int offset = 0;
        int length = input.readableBytes();
        if (input.hasArray()) {
        	byte[] inputBytes = input.array();
            offset = input.arrayOffset() + input.readerIndex();
            copy = Arrays.copyOfRange(inputBytes, offset, offset + length);
            return copy;
        } else {
        	copy = new byte[length];
            input.getBytes(input.readerIndex(), copy);
        }
        return copy;
    }
