	public void setHelpAvailable(boolean b) {
		isHelpAvailable = b;
	}

	public void setNeedsProgressMonitor(boolean b) {
		needsProgressMonitor = b;
	}

	public void setTitleBarColor(RGB color) {
		titleBarColor = color;
	}

	public void setWindowTitle(String newTitle) {
		windowTitle = newTitle;
		if (container != null) {
