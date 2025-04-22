    @Override
    public boolean shutdown() {
        PAUSE_DETECTOR.shutdown();
        return super.shutdown();
    }

