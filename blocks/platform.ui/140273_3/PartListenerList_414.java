	private void fireEvent(SafeRunnable runnable, IPartListener listener, IWorkbenchPart part, String description) {
		String label = null;// for debugging
		if (UIStats.isDebugging(UIStats.NOTIFY_PART_LISTENERS)) {
			label = description + part.getTitle();
			UIStats.start(UIStats.NOTIFY_PART_LISTENERS, label);
		}
		SafeRunner.run(runnable);
		if (UIStats.isDebugging(UIStats.NOTIFY_PART_LISTENERS)) {
