	public ObjectContributorManager() {
		contributors = new Hashtable(5);
		contributorRecordSet = new HashSet(5);
		objectLookup = null;
		resourceAdapterLookup = null;
		adaptableLookup = null;
		String extensionPointId = getExtensionPointFilter();
		if (extensionPointId != null) {
			IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(PlatformUI.PLUGIN_ID,
					extensionPointId);
			IExtensionTracker tracker = PlatformUI.getWorkbench().getExtensionTracker();
			tracker.registerHandler(this, ExtensionTracker.createExtensionPointFilter(extensionPoint));
