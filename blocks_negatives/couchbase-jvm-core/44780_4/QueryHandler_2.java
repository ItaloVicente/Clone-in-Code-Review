    /**
     * Parses the error portion of the query response.
     *
     * @param last if this batch is the last one.
     */
    private void parseQueryError(boolean last) {
        if (!last) {
            return;
        }

        short found = 0;
        int foundIndex = 0;
        for (int i = responseContent.writerIndex(); i > responseContent.readerIndex(); i--) {
            if (responseContent.getByte(i) == 125) {
                found++;
            }
            if (found == 2) {
                foundIndex = i;
                break;
            }
        }

        int length = foundIndex - responseContent.readerIndex() + 1;
        queryInfoObservable.onNext(responseContent.copy(responseContent.readerIndex(), length));
        queryInfoObservable.onCompleted();
        queryParsingState = QUERY_STATE_DONE;
    }

