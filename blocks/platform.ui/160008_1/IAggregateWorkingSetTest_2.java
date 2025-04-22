		IWorkingSetManager workingSetManager = PlatformUI.getWorkbench().getWorkingSetManager();
		IWorkingSet set = workingSetManager.getWorkingSet(AGGREGATE_WORKING_SET_NAME_);
		if (set.isAggregateWorkingSet()) {
			IWorkingSet[] sets = ((IAggregateWorkingSet) set).getComponents();
			if (sets.length >= 1) {
				sets[0] = null; // client fails to pay enough attention to specs or unknowingly does this
