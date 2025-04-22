    private static final PauseDetector PAUSE_DETECTOR = new SimplePauseDetector(
        TimeUnit.MILLISECONDS.toNanos(10),
        TimeUnit.MILLISECONDS.toNanos(10),
        3
    );

    static {
        LatencyStats.setDefaultPauseDetector(PAUSE_DETECTOR);
        Runtime.getRuntime().addShutdownHook(new Thread("cb-shutdown-pd") {
            @Override
            public void run() {
                PAUSE_DETECTOR.shutdown();
