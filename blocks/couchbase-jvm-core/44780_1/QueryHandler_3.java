    private byte transitionToNextToken() {
        int endNextToken = responseContent.bytesBefore((byte) ':');
        ByteBuf peekSlice = responseContent.readSlice(endNextToken + 1);
        String peek = peekSlice.toString(CHARSET);
        if (peek.contains("\"signature\"")) {
            return QUERY_STATE_SIGNATURE;
        } else if (peek.contains("\"results\"")) {
            return QUERY_STATE_ROWS;
        } else if (peek.contains("\"status\"")) {
            return QUERY_STATE_STATUS;
        } else if (peek.contains("\"errors\"")) {
            return QUERY_STATE_ERROR;
        } else if (peek.contains("\"warnings\"")) {
            return QUERY_STATE_WARNING;
        } else if (peek.contains("\"metrics\"")) {
            return QUERY_STATE_INFO;
        } else {
            IllegalStateException e = new IllegalStateException("Error parsing query response (in TRANSITION) at " + peek);
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace(e);
                LOGGER.trace(responseContent.toString(CHARSET));
            }
            throw e;
        }
    }

    private int findEnd(char openingChar, char closingChar) {
        int closePos = -1;
        int openCount = 0;
        for (int i = responseContent.readerIndex(); i <= responseContent.writerIndex(); i++) {
            byte current = responseContent.getByte(i);
            if (current == openingChar) {
                openCount++;
            } else if (current == closingChar && openCount > 0) {
                openCount--;
                if (openCount == 0) {
                    closePos = i;
                    break;
                }
