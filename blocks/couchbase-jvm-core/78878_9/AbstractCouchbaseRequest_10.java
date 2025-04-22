    @Override
    public void retryAfter(long after) {
        this.retryAfter = after;
    }

    @Override
    public long retryAfter() {
        return this.retryAfter;
    }

    @Override
    public void maxRetryDuration(long maxRetryDuration) {
        this.maxRetryDuration = maxRetryDuration;
    }
    @Override
    public long maxRetryDuration() { return this.maxRetryDuration; }

    @Override
    public void retryDelay(Delay retryDelay) {
        this.retryDelay = retryDelay;
    }

    @Override
    public Delay retryDelay() {
        return this.retryDelay;
    }

