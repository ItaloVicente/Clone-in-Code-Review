    private void parseQueryStatus() {
        queryRowObservable.onCompleted();
        queryErrorObservable.onCompleted();

        responseContent.skipBytes(bytesBeforeInResponse('"') + 1);
        ByteBuf resultSlice = responseContent.readSlice(bytesBeforeInResponse('"'));
        queryStatusObservable.onNext(resultSlice.toString(CHARSET));
        queryStatusObservable.onCompleted();
        queryParsingState = transitionToNextToken();
    }

