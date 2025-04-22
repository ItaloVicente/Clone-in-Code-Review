	public EditorRegistry(IContentTypeManager contentTypeManager) {
		super();
		this.contentTypeManager = contentTypeManager;
		initializeFromStorage();
		IExtensionTracker tracker = PlatformUI.getWorkbench().getExtensionTracker();
		tracker.registerHandler(this, ExtensionTracker.createExtensionPointFilter(getExtensionPointFilter()));
