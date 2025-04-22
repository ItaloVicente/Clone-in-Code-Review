
	private WorkingSetManager getWorkingSetManager() {
		IWorkingSetManager manager = getManager();
		if (manager instanceof WorkingSetManager) {
			return (WorkingSetManager) manager;
		}
		return null;
	}

	private WorkingSetDescriptor getDescriptor() {
		return getDescriptor(null);
	}

	private IWorkingSetUpdater2 getUpdater() {
		WorkingSetDescriptor descriptor = getDescriptor();
		WorkingSetManager manager = getWorkingSetManager();
		if (manager != null && descriptor != null) {
			IWorkingSetUpdater updater = manager.getUpdater(descriptor);
			if (updater instanceof IWorkingSetUpdater2) {
				return (IWorkingSetUpdater2) updater;
			}
		}
		return null;
	}
