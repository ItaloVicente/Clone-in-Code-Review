    /**
     * Returns the reconnect retry delay in  milliseconds.
     *
     * It uses an exponential back-off algorithm (2^attempt) until a fixed
     * ceiling is reached ({@link #MAX_RECONNECT_DELAY}). If the computed delay is below
     * {@link #MIN_RECONNECT_DELAY}, then this one is returned instead.
     *
     * @return the retry delay.
     */
    private long reconnectDelay() {
        int delay = 1 << (reconnectAttempt++);
        if (delay <= MIN_RECONNECT_DELAY) {
            return MIN_RECONNECT_DELAY;
        }
        return delay >= MAX_RECONNECT_DELAY ? MAX_RECONNECT_DELAY : delay;
    }

