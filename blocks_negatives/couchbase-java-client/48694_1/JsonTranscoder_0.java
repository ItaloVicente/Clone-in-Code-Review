    /**
     * Converts a {@link ByteBuf} to a {@link JsonObject}, <b>without releasing the buffer</b>
     *
     * @param input the buffer to convert. It won't be cleared (contrary to {@link #doDecode(String, ByteBuf, long, int, int, ResponseStatus) classical decode})
     * @return a JsonObject decoded from the buffer
     * @throws Exception
     */
    public JsonObject byteBufToJsonObject(ByteBuf input) throws Exception {
