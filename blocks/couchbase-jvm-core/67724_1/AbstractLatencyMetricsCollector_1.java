    @Override
    public boolean shutdown() {
        if (pauseDetectorHeld.compareAndSet(true, false))
            releasePauseDetector();
        return super.shutdown();
    }

