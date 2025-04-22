        return recentWorkingSets.toArray(new IWorkingSet[recentWorkingSets.size()]);
    }

    /**
     * Adds the specified working set to the list of recently used
     * working sets.
     *
     * @param workingSet working set to added to the list of recently
     * 	used working sets.
     */
    protected void internalAddRecentWorkingSet(IWorkingSet workingSet) {
    		if (!workingSet.isVisible()) {
				return;
			}
        recentWorkingSets.remove(workingSet);
        recentWorkingSets.add(0, workingSet);
