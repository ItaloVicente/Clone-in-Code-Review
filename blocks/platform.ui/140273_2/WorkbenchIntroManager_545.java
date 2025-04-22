	private final Workbench workbench;

	WorkbenchIntroManager(Workbench workbench) {
		this.workbench = workbench;
		workbench.getExtensionTracker().registerHandler(new IExtensionChangeHandler() {

			@Override
			public void addExtension(IExtensionTracker tracker, IExtension extension) {
			}
