    private final static Object PAUSE_DETECTOR_LOCK = new Object();
    private static int pauseDetectorCount = 0;
    private static PauseDetector staticPauseDetector;

    private static PauseDetector acquirePauseDetector() {
        synchronized (PAUSE_DETECTOR_LOCK) {
            if (pauseDetectorCount++ == 0) {
                staticPauseDetector = new SimplePauseDetector(
                    TimeUnit.MILLISECONDS.toNanos(10),
                    TimeUnit.MILLISECONDS.toNanos(10),
                    3
                );
            }
            return staticPauseDetector;
        }
    }

    private static void releasePauseDetector() {
        synchronized (PAUSE_DETECTOR_LOCK) {
            if (--pauseDetectorCount == 0) {
                staticPauseDetector.shutdown();
                staticPauseDetector = null; // help GC
