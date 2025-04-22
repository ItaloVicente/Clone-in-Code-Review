    public int totalReceivedBytes() {
        return totalReceivedBytes;
    }

    public void inc(int delta) {
        totalReceivedBytes += delta;
    }

    public void reset() {
        totalReceivedBytes = 0;
    }

