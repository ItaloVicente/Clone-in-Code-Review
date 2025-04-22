	private void fireEvent(SafeRunnable runnable, IPageListener listener, IWorkbenchPage page, String description) {
		String label = null;// for debugging
		if (UIStats.isDebugging(UIStats.NOTIFY_PAGE_LISTENERS)) {
			label = description + page.getLabel();
			UIStats.start(UIStats.NOTIFY_PAGE_LISTENERS, label);
		}
		SafeRunner.run(runnable);
		if (UIStats.isDebugging(UIStats.NOTIFY_PAGE_LISTENERS)) {
