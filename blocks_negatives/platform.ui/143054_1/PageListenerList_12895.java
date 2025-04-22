    /**
     * Calls a page listener with associated performance event instrumentation
     *
     * @param runnable
     * @param listener
     * @param page
     * @param description
     */
    private void fireEvent(SafeRunnable runnable, IPageListener listener, IWorkbenchPage page, String description) {
    	if (UIStats.isDebugging(UIStats.NOTIFY_PAGE_LISTENERS)) {
    		label = description + page.getLabel();
    		UIStats.start(UIStats.NOTIFY_PAGE_LISTENERS, label);
    	}
    	SafeRunner.run(runnable);
    	if (UIStats.isDebugging(UIStats.NOTIFY_PAGE_LISTENERS)) {
