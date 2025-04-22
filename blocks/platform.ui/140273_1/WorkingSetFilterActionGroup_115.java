	public IWorkingSet getWorkingSet() {
		return workingSet;
	}

	public void setWorkingSet(IWorkingSet newWorkingSet) {
		IWorkingSet oldWorkingSet = workingSet;

		workingSet = newWorkingSet;
		clearWorkingSetAction.setEnabled(newWorkingSet != null);
		editWorkingSetAction.setEnabled(newWorkingSet != null && newWorkingSet.isEditable());

		firePropertyChange(newWorkingSet, oldWorkingSet);
	}

