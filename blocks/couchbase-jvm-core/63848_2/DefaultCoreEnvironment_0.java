    public boolean shutdown() {
        return shutdown(disconnectTimeout(), TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean shutdown(long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(shutdownAsync(), timeout, timeUnit);
