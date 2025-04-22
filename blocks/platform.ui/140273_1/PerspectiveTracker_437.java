	protected PerspectiveTracker(IWorkbenchWindow window) {
		Assert.isNotNull(window);
		this.window = window;
		window.addPageListener(this);
		window.addPerspectiveListener(this);
	}
