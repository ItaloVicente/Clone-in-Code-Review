    private void parseQueryInitial() {
        ByteBuf content = responseContent;

        if (content.readableBytes() >= 20) {
            ByteBuf prefixBuf = content.slice(0, 20);
            String prefix = prefixBuf.toString(CHARSET);

            if (prefix.contains("resultset")) {
                queryParsingState = QUERY_STATE_ROWS;
            } else if (prefix.contains("error")) {
                queryRowObservable.onCompleted();
                queryParsingState = QUERY_STATE_ERROR;
            } else {
                throw new IllegalStateException("Error parsing query response (in INITIAL): "
                    + content.toString(CHARSET));
