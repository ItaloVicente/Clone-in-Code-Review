        return closePos;
    }

    private void parseQuerySignature() {
        int openPos = responseContent.bytesBefore((byte) '{');
        int closePos = findEnd('{', '}');
        if (closePos > 0) {
            int length = closePos - openPos - responseContent.readerIndex() + 1;
            responseContent.skipBytes(openPos);
            ByteBuf signature = responseContent.readSlice(length);
        }
        queryParsingState = transitionToNextToken();
