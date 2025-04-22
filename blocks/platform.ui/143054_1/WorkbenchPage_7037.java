		IWorkingSet oldWorkingSet = workingSet;

		workingSet = newWorkingSet;
		if (oldWorkingSet != newWorkingSet) {
			firePropertyChange(CHANGE_WORKING_SET_REPLACE, oldWorkingSet, newWorkingSet);
		}
		if (newWorkingSet != null) {
			WorkbenchPlugin.getDefault().getWorkingSetManager()
					.addPropertyChangeListener(workingSetPropertyChangeListener);
		} else {
			WorkbenchPlugin.getDefault().getWorkingSetManager()
					.removePropertyChangeListener(workingSetPropertyChangeListener);
		}
	}

	@Override
