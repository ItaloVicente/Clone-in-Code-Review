    
    /**
     * Enables or disables listener notifications. This is used to delay listener notifications until the
     * end of a public method.
     * 
     * @param shouldDefer
     */
    private void deferUpdates(boolean shouldDefer) {
        if (shouldDefer) {
            if (deferCount == 0) {
                startDeferring();
            }
            deferCount++;
        } else {
            deferCount--;
            if (deferCount == 0) {
                handleDeferredEvents();
            }
        }
    }
    
    private void startDeferring() {
