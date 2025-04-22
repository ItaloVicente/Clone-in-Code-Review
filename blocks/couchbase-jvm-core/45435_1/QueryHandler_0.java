        if (openPos < nextColon) { //checks for empty signature
            int closePos = findSectionClosingPosition(responseContent, '{', '}');
            if (closePos > 0) {
                int length = closePos - openPos - responseContent.readerIndex() + 1;
                responseContent.skipBytes(openPos);
                ByteBuf signature = responseContent.readSlice(length);
            }
