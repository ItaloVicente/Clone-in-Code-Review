	protected void saveDefaultWorkingSet(IMemento memento) {
		String workingSetName;
		if (this.defaultWorkingSet == null) {
			workingSetName = ""; //$NON-NLS-1$
		} else {
			workingSetName = this.defaultWorkingSet.getName();
		}
		memento.putString(AbstractWorkingSetManager.TAG_DEFAULT_WORKING_SET_NAME, workingSetName);
    }

	protected void restoreDefaultWorkingSet(IMemento memento) {
		String defaultWorkingSetName = memento
				.getString(AbstractWorkingSetManager.TAG_DEFAULT_WORKING_SET_NAME);
		if (defaultWorkingSetName != null) {
			IWorkingSet workingSet = getWorkingSet(defaultWorkingSetName);
			if (workingSet != null) {
				setDefaultWorkingSet(workingSet);
			}
		}
    }

