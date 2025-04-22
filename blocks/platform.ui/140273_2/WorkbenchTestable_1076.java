	public void init(Display display, IWorkbench workbench) {
		Assert.isNotNull(display);
		Assert.isNotNull(workbench);
		this.display = display;
		this.workbench = workbench;
		if (getTestHarness() != null) {
			Runnable runnable = () -> {
