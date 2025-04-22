        activeEntry.location.update();
    }

    /*
     * Perform the forward action by getting the next location and restoring
     * its context.
     * <p>
     * (Called by NavigationHistoryAction)
     * </p>
     */
    void forward() {
    	if (isPerTabHistoryEnabled()) {
    		forwardForTab();
    		return;
    	}
        if (canForward()) {
