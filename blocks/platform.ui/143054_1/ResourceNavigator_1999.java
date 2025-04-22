		} catch (NumberFormatException e) {
		}
		setComparator(new ResourceComparator(sortType));
	}

	protected void initWorkingSetFilter() {
		String workingSetName = settings.get(STORE_WORKING_SET);

		IWorkingSet workingSet = null;

		if (workingSetName != null && workingSetName.isEmpty() == false) {
			IWorkingSetManager workingSetManager = getPlugin().getWorkbench().getWorkingSetManager();
