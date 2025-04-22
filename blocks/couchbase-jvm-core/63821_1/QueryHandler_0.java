    private void decideBetweenRawAndObjects(boolean lastChunk) {
        responseContent.markReaderIndex();
        int openArrayPos = findNextChar(responseContent, '[');
        if (openArrayPos > -1) {
            responseContent.skipBytes(openArrayPos + 1);
        } else {
            responseContent.resetReaderIndex();
            return; //more data
        }

        int spaceToSkip = responseContent.forEachByte(new WhitespaceSkipper());
        if (spaceToSkip > -1) {
            responseContent.readerIndex(spaceToSkip);
        }

        if (responseContent.isReadable()) {
            byte first = responseContent.getByte(responseContent.readerIndex());
            if (first == '{') {
                queryParsingState = QUERY_STATE_ROWS;
            } else if (first == ']') {
                queryParsingState = transitionToNextToken(lastChunk);
            } else {
                queryParsingState = QUERY_STATE_ROWS_RAW;
            }
        } else {
            responseContent.resetReaderIndex();
        }
    }

