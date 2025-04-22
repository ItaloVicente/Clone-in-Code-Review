            int length = closeBracketPos - openBracketPos - responseContent.readerIndex() + 1;
            responseContent.skipBytes(openBracketPos);
            ByteBuf resultSlice = responseContent.readSlice(length);
            queryRowObservable.onNext(resultSlice.copy());
        }

        responseContent.discardReadBytes();
    }

    private void parseQueryError() {
        while (true) {
            int openBracketPos = responseContent.bytesBefore((byte) '{');
            int nextColonPos = responseContent.bytesBefore((byte) ':');
            if (nextColonPos < openBracketPos) {
                queryParsingState = transitionToNextToken(); //warnings or status
                break;
            }

            int closeBracketPos = findEnd('{', '}');
