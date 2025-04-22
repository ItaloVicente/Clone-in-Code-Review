            throw e;
        }
    }

    private void skipQuerySignature() {
        int openPos = bytesBeforeInResponse('{');
        int closePos = findSectionClosingPosition(responseContent, '{', '}');
        if (closePos > 0) {
            int length = closePos - openPos - responseContent.readerIndex() + 1;
            responseContent.skipBytes(openPos);
            ByteBuf signature = responseContent.readSlice(length);
