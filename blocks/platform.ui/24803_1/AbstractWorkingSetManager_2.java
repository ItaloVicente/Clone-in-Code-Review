	@Override
	public IWorkingSet getDefaultWorkingSet() {
		return this.defaultWorkingSet;
	}

	@Override
	public void setDefaultWorkingSet(IWorkingSet workingSet) {
		if (workingSet != this.defaultWorkingSet) {
			IWorkingSet previous = this.defaultWorkingSet;
			this.defaultWorkingSet = workingSet;
			firePropertyChange(IWorkingSetManager.CHANGE_WORKING_SET_DEFAULT, previous,
					this.defaultWorkingSet);
		}
	}

