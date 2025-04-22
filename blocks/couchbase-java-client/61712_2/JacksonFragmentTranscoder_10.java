    protected <T> ByteBuf doEncodeSingle(T value, String transcodingErrorMessage) throws TranscodingException {
        try {
            return Unpooled.wrappedBuffer(mapper.writeValueAsBytes(value));
        } catch (JsonProcessingException e) {
            throw new TranscodingException(transcodingErrorMessage, e);
        }
