	public final void remove(Window window) {
		if (windows.contains(window)) {
			windows.remove(window);
			window.setWindowManager(null);
		}
	}
