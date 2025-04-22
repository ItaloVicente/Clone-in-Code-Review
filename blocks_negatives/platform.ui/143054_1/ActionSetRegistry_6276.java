    /**
     * Creates the action set registry.
     */
    public ActionSetRegistry() {
    	contextService = PlatformUI
				.getWorkbench().getService(IContextService.class);
		PlatformUI.getWorkbench().getExtensionTracker().registerHandler(
                this,
                ExtensionTracker
                        .createExtensionPointFilter(new IExtensionPoint[] {
                                getActionSetExtensionPoint(),
                                getActionSetPartAssociationExtensionPoint() }));
        readFromRegistry();
    }

    /**
     * Return the action set part association extension point.
     *
     * @return the action set part association extension point
     * @since 3.1
     */
    private IExtensionPoint getActionSetPartAssociationExtensionPoint() {
        return Platform
        .getExtensionRegistry().getExtensionPoint(
                PlatformUI.PLUGIN_ID,
                IWorkbenchRegistryConstants.PL_ACTION_SET_PART_ASSOCIATIONS);
    }

    /**
     * Return the action set extension point.
     *
     * @return the action set extension point
     * @since 3.1
     */
    private IExtensionPoint getActionSetExtensionPoint() {
        return Platform
                .getExtensionRegistry().getExtensionPoint(
                        PlatformUI.PLUGIN_ID,
                        IWorkbenchRegistryConstants.PL_ACTION_SETS);
    }

    /**
     * Adds an action set.
     * @param desc
     */
    private void addActionSet(ActionSetDescriptor desc) {
