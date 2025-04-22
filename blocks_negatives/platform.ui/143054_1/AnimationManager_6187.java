    void setAnimated(final boolean bool) {
        animated = bool;
        animationUpdateJob.schedule(100);
    }

    /**
     * Dispose the images in the receiver.
     */
    void dispose() {
        setAnimated(false);
        ProgressManager.getInstance().removeListener(listener);
    }

    private IJobProgressManagerListener getProgressListener() {
        return new IJobProgressManagerListener() {
