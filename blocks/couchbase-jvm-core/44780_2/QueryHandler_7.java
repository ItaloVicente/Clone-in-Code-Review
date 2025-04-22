    private void parseQueryStatus() {
        queryRowObservable.onCompleted();
        queryErrorObservable.onCompleted();

        responseContent.skipBytes(responseContent.bytesBefore((byte) '"') + 1);
        ByteBuf resultSlice = responseContent.readSlice(responseContent.bytesBefore((byte) '"'));
        queryStatusObservable.onNext(resultSlice.toString(CHARSET));
        queryStatusObservable.onCompleted();
        queryParsingState = transitionToNextToken();
    }

