    private IWorkingSetUpdater getUpdater(WorkingSetDescriptor descriptor) {
		IWorkingSetUpdater updater= (IWorkingSetUpdater)updaters.get(descriptor.getId());
    	if (updater == null) {
    		updater= descriptor.createWorkingSetUpdater();
    		if (updater == null) {
    			updater= NULL_UPDATER;
    		} else {
    			firePropertyChange(CHANGE_WORKING_SET_UPDATER_INSTALLED, null, updater);
    			PlatformUI.getWorkbench().getExtensionTracker().registerObject(
						descriptor.getConfigurationElement()
								.getDeclaringExtension(), updater,
