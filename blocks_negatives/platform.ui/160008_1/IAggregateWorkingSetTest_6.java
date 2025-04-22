		IWorkingSetManager workingSetManager = fWorkbench
		.getWorkingSetManager();
		IWorkingSet set=workingSetManager.getWorkingSet(AGGREGATE_WORKING_SET_NAME_);
		if(set.isAggregateWorkingSet()){
			IWorkingSet[] sets=((IAggregateWorkingSet)set).getComponents();
			if(sets.length>1){
				sets[0]=workingSetManager.createWorkingSet(WORKING_SET_NAME, new IAdaptable[] { fWorkspace.getRoot() });
