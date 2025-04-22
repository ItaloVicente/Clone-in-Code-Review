    private void reset() {
        currentRequest = null;
        currentChunk.release();
        currentChunk = null;
        currentState = ParsingState.INITIAL;
        currentCode = 0;
    }

