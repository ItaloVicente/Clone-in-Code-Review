		tracker = extensionTracker;
		targetID = id;
		pageLayout = out;
		readRegistry(Platform.getExtensionRegistry(), PlatformUI.PLUGIN_ID,
				IWorkbenchRegistryConstants.PL_PERSPECTIVE_EXTENSIONS);
	}

	private boolean includeTag(String tag) {
		return includeOnlyTags == null || includeOnlyTags.contains(tag);
	}

	private boolean processActionSet(IConfigurationElement element) {
		String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
		if (id != null) {
