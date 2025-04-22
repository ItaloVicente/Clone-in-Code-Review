	public KeyBindingService(IWorkbenchPartSite workbenchPartSite) {
		this(workbenchPartSite, null);
	}

	KeyBindingService(IWorkbenchPartSite workbenchPartSite, KeyBindingService parent) {
		this.workbenchPartSite = workbenchPartSite;
