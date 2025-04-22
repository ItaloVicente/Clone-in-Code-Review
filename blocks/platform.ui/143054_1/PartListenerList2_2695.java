	public PartListenerList2() {
		super();
	}

	public void addPartListener(IPartListener2 l) {
		addListenerObject(l);
	}

	private void fireEvent(SafeRunnable runnable, IPartListener2 listener, IWorkbenchPartReference ref, String string) {
		String label = null;// for debugging
		if (UIStats.isDebugging(UIStats.NOTIFY_PART_LISTENERS)) {
			label = string + ref.getTitle();
			UIStats.start(UIStats.NOTIFY_PART_LISTENERS, label);
		}
		SafeRunner.run(runnable);
		if (UIStats.isDebugging(UIStats.NOTIFY_PART_LISTENERS)) {
