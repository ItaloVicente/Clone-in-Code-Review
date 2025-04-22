		if (workingSetRemoved) {
			((AbstractWorkingSet) workingSet).disconnect();
			removeFromUpdater(workingSet);
			firePropertyChange(CHANGE_WORKING_SET_REMOVE, workingSet, null);
		}
		return workingSetRemoved || recentWorkingSetRemoved;
	}
