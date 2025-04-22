    /**
     * Calls a perspective listener with associated performance event instrumentation
     *
     * @param runnable
     * @param listener
     * @param perspective
     * @param description
     */
    private void fireEvent(SafeRunnable runnable, IPerspectiveListener listener, IPerspectiveDescriptor perspective, String description) {
    	if (UIStats.isDebugging(UIStats.NOTIFY_PERSPECTIVE_LISTENERS)) {
    		label = description + perspective.getId();
    		UIStats.start(UIStats.NOTIFY_PERSPECTIVE_LISTENERS, label);
    	}
    	SafeRunner.run(runnable);
    	if (UIStats.isDebugging(UIStats.NOTIFY_PERSPECTIVE_LISTENERS)) {
