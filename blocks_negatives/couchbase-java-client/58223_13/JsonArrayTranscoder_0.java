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
        return JacksonTransformers.MAPPER.readValue(inputBytes, offset, length, JsonArray.class);
