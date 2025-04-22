	@Override
	protected void workingSetAdded(IWorkingSet addedSet) {
		viewer.setChecked(addedSet, true);
		updateButtonAvailability();
	}

