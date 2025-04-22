		IWorkingSetManager workingSetManager = PlatformUI.getWorkbench()
				.getWorkingSetManager();
		WORKING_SET = workingSetManager.getWorkingSet(WORKING_SET_NAME);
		if (WORKING_SET == null) {
			WORKING_SET = workingSetManager.createWorkingSet(WORKING_SET_NAME,
					new IAdaptable[0]);
			workingSetManager.addWorkingSet(WORKING_SET);
		}
