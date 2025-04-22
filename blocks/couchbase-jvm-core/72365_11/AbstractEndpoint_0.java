    public void notifyResponseDecoded() {
        free = true;
        lastResponse = System.nanoTime();
    }

    @Override
    public long lastResponse() {
        return lastResponse;
    }

