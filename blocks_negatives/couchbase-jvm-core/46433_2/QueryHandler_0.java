    /**
     * Shorthand for bytesBeforeInResponse(c)), finds the number of bytes until next occurrence of
     * the character c from the current readerIndex() in responseContent.
     *
     * @param c the character to search for.
     * @return the number of bytes before the character or -1 if not found.
     * @see ByteBuf#bytesBefore(byte)
     */
    private int bytesBeforeInResponse(char c) {
        return responseContent.bytesBefore((byte) c);
    }

