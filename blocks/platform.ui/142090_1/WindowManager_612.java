	public void add(Window window) {
		if (!windows.contains(window)) {
			windows.add(window);
			window.setWindowManager(this);
		}
	}
