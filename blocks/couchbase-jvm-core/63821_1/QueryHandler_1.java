    private void parseQueryRowsRaw(boolean lastChunk) {
        while (responseContent.isReadable()) {
            int splitPos = findSplitPosition(responseContent, ',');
            int arrayEndPos = findSplitPosition(responseContent, ']');

            if (splitPos == -1 && arrayEndPos == -1) {
                break;
            } else if (arrayEndPos > 0 && (arrayEndPos < splitPos || splitPos == -1)) {
                splitPos = arrayEndPos;
                sectionDone();
            }

            int length = splitPos - responseContent.readerIndex();
            ByteBuf resultSlice = responseContent.readSlice(length);
            queryRowObservable.onNext(resultSlice.copy());
            responseContent.skipBytes(1);
            responseContent.discardReadBytes();

            if (sectionDone) {
                queryParsingState = transitionToNextToken(lastChunk);
                break;
            }
        }
    }

