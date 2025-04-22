    private boolean shouldSendKeepAlive() {
        if (pipeline) {
            return true; // always send if pipelining is enabled
        }

        return sentRequestQueue.isEmpty() && currentDecodingState == DecodingState.INITIAL;
    }

