    }

    /*
     * Perform the backward action by getting the previous location and restoring
     * its context.
     * <p>
     * (Called by NavigationHistoryAction)
     * </p>
     */
    void backward() {
    	if (isPerTabHistoryEnabled()) {
    		backwardForTab();
    		return;
    	}
        if (canBackward()) {
