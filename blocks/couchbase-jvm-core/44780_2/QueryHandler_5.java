    private int findEnd(char openingChar, char closingChar) {
        int closePos = -1;
        int openCount = 0;
        for (int i = responseContent.readerIndex(); i <= responseContent.writerIndex(); i++) {
            byte current = responseContent.getByte(i);
            if (current == openingChar) {
                openCount++;
            } else if (current == closingChar && openCount > 0) {
                openCount--;
                if (openCount == 0) {
                    closePos = i;
                    break;
                }
            }
        }
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
    }

