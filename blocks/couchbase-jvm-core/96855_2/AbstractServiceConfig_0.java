    protected void checkIdleTime(final int idleTime) {
        if (idleTime > 0 && idleTime < 10) {
            throw new IllegalArgumentException("Idle time must either be 0 (disabled) or greater than 9 seconds");
        }
    }

