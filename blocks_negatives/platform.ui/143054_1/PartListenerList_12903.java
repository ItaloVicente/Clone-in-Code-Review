    /**
     * Calls a part listener with associated performance event instrumentation
     *
     * @param runnable
     * @param listener
     * @param part
     * @param description
     */
    private void fireEvent(SafeRunnable runnable, IPartListener listener, IWorkbenchPart part, String description) {
    	if (UIStats.isDebugging(UIStats.NOTIFY_PART_LISTENERS)) {
    		label = description + part.getTitle();
    		UIStats.start(UIStats.NOTIFY_PART_LISTENERS, label);
    	}
    	SafeRunner.run(runnable);
    	if (UIStats.isDebugging(UIStats.NOTIFY_PART_LISTENERS)) {
