        int endStatus = findNextChar(responseContent, '"');
        if (endStatus > -1) {
            ByteBuf resultSlice = responseContent.readSlice(endStatus);
            queryStatusObservable.onNext(resultSlice.toString(CHARSET));
            queryStatusObservable.onCompleted();
            sectionDone();
            queryParsingState = transitionToNextToken(lastChunk);
        } else {
            responseContent.resetReaderIndex();
            return; //need more data
        }
