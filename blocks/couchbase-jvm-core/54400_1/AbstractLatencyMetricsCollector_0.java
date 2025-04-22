    private static final PauseDetector PAUSE_DETECTOR = new SimplePauseDetector(
        TimeUnit.SECONDS.toNanos(1),
        TimeUnit.SECONDS.toNanos(1),
        3
    );

    static {
        LatencyStats.setDefaultPauseDetector(PAUSE_DETECTOR);
    }

