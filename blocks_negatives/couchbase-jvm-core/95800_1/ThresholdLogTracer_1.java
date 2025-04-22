    /**
     * Returns the current time, trimmed to micros precision.
     */
    public static long currentTimeMicros() {
        return TimeUnit.NANOSECONDS.toMicros(System.nanoTime());
    }

