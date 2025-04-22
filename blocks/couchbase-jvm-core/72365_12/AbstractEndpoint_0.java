    public void notifyResponseDecoded(boolean hidden) {
        free = true;
        if (!hidden) {
            lastResponse = System.nanoTime();
        }
    }

    @Override
    public long lastResponse() {
        return lastResponse;
    }

