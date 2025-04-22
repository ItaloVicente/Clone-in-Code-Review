    private int bytesBeforeInResponse(char c) {
        return responseContent.bytesBefore((byte) c);
    }

    private static int findSectionClosingPosition(ByteBuf buf, char openingChar, char closingChar) {
        int closePos = -1;
        int openCount = 0;
        for (int i = buf.readerIndex(); i <= buf.writerIndex(); i++) {
            byte current = buf.getByte(i);
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

