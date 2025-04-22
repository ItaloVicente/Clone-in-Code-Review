		return recentWorkingSets.toArray(new IWorkingSet[recentWorkingSets.size()]);
	}

	protected void internalAddRecentWorkingSet(IWorkingSet workingSet) {
		if (!workingSet.isVisible()) {
			return;
		}
		recentWorkingSets.remove(workingSet);
		recentWorkingSets.add(0, workingSet);
