
            int closeBracketPos = findSectionClosingPosition(responseContent, '{', '}');
            if (closeBracketPos == -1) {
                break;
            }

            int length = closeBracketPos - openBracketPos - responseContent.readerIndex() + 1;
            responseContent.skipBytes(openBracketPos);
            ByteBuf resultSlice = responseContent.readSlice(length);
            queryRowObservable.onNext(resultSlice.copy());
        }

        responseContent.discardReadBytes();
    }

    private void parseQueryError() {
        while (true) {
            int openBracketPos = bytesBeforeInResponse('{');
            int nextColonPos = bytesBeforeInResponse(':');
            if (nextColonPos < openBracketPos) {
                queryParsingState = transitionToNextToken(); //warnings or status
                break;
