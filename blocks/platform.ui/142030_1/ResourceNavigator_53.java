		}
	};

	private IPropertyChangeListener propertyChangeListener = event -> {
		String property = event.getProperty();
		Object newValue = event.getNewValue();
		Object oldValue = event.getOldValue();

		if (IWorkingSetManager.CHANGE_WORKING_SET_REMOVE.equals(property) && oldValue == workingSet) {
			setWorkingSet(null);
		} else if (IWorkingSetManager.CHANGE_WORKING_SET_NAME_CHANGE.equals(property) && newValue == workingSet) {
			updateTitle();
		} else if (IWorkingSetManager.CHANGE_WORKING_SET_CONTENT_CHANGE.equals(property) && newValue == workingSet) {
