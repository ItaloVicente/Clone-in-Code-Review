	public ActionSetRegistry() {
		contextService = PlatformUI.getWorkbench().getService(IContextService.class);
		PlatformUI.getWorkbench().getExtensionTracker().registerHandler(this,
				ExtensionTracker.createExtensionPointFilter(new IExtensionPoint[] { getActionSetExtensionPoint(),
						getActionSetPartAssociationExtensionPoint() }));
		readFromRegistry();
	}

	private IExtensionPoint getActionSetPartAssociationExtensionPoint() {
		return Platform.getExtensionRegistry().getExtensionPoint(PlatformUI.PLUGIN_ID,
				IWorkbenchRegistryConstants.PL_ACTION_SET_PART_ASSOCIATIONS);
	}

	private IExtensionPoint getActionSetExtensionPoint() {
		return Platform.getExtensionRegistry().getExtensionPoint(PlatformUI.PLUGIN_ID,
				IWorkbenchRegistryConstants.PL_ACTION_SETS);
	}

	private void addActionSet(ActionSetDescriptor desc) {
