		IWorkingSet[] sets = window.getWorkbench().getWorkingSetManager()
				.getRecentWorkingSets();
		BuildSetAction last = BuildSetAction.lastBuilt;
		IWorkingSet lastSet = null;
		int accel = 1;
		if (last != null) {
