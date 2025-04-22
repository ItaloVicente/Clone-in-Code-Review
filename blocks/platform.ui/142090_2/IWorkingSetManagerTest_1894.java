		super.doSetUp();
		fWorkingSetManager = fWorkbench.getWorkingSetManager();
		fWorkspace = ResourcesPlugin.getWorkspace();
		fWorkingSet = fWorkingSetManager.createWorkingSet(WORKING_SET_NAME_1,
				new IAdaptable[] { fWorkspace.getRoot() });

		IWorkingSet[] workingSets = fWorkingSetManager.getWorkingSets();
		for (IWorkingSet workingSet : workingSets) {
			fWorkingSetManager.removeWorkingSet(workingSet);
		}
	}

	void resetChangeData() {
		fChangeProperty = "";
		fChangeNewValue = null;
		fChangeOldValue = null;
	}

	public void testConfigBlockFilter() {
		final String [] setIds = new String[] {"5", "2", "4", "1", "3" };

		IWorkingSet [] sets = new IWorkingSet[setIds.length * 3];
		for (int i = 0; i < setIds.length; i++) {
