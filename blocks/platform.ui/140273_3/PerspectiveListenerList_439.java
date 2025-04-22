	private void fireEvent(SafeRunnable runnable, IPerspectiveListener listener, IPerspectiveDescriptor perspective,
			String description) {
		String label = null;// for debugging
		if (UIStats.isDebugging(UIStats.NOTIFY_PERSPECTIVE_LISTENERS)) {
			label = description + perspective.getId();
			UIStats.start(UIStats.NOTIFY_PERSPECTIVE_LISTENERS, label);
		}
		SafeRunner.run(runnable);
		if (UIStats.isDebugging(UIStats.NOTIFY_PERSPECTIVE_LISTENERS)) {
