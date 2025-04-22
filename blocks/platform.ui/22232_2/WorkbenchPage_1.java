	private AggregateWorkingSet findAggregateWorkingSet(IWorkingSetManager workingSetManager) {
		for (IWorkingSet workingSet : workingSetManager.getAllWorkingSets()) {
			if (workingSet instanceof AggregateWorkingSet) {
				return (AggregateWorkingSet) workingSet;
			}
		}
		return null;
	}

