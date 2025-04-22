	private void removeWorkingSet(String name) {
		IWorkingSetManager workingSetManager = PlatformUI.getWorkbench()
				.getWorkingSetManager();
		IWorkingSet workingSet = workingSetManager.getWorkingSet(name);
		if (workingSet != null)
			workingSetManager.removeWorkingSet(workingSet);
	}

