    private IPropertyChangeListener propertyChangeListener = new IPropertyChangeListener() {
        @Override
		public void propertyChange(PropertyChangeEvent event) {
            String property = event.getProperty();
            Object newValue = event.getNewValue();
            Object oldValue = event.getOldValue();

            if (IWorkingSetManager.CHANGE_WORKING_SET_REMOVE.equals(property)
                    && oldValue == workingSet) {
                setWorkingSet(null);
            } else if (IWorkingSetManager.CHANGE_WORKING_SET_NAME_CHANGE
                    .equals(property)
                    && newValue == workingSet) {
                updateTitle();
            } else if (IWorkingSetManager.CHANGE_WORKING_SET_CONTENT_CHANGE
                    .equals(property)
                    && newValue == workingSet) {
				if (workingSet.isAggregateWorkingSet() && workingSet.isEmpty()) {
					if (!emptyWorkingSet) {
						emptyWorkingSet = true;
						workingSetFilter.setWorkingSet(null);
					}
				} else {
					if (emptyWorkingSet) {
					    emptyWorkingSet = false;
						workingSetFilter.setWorkingSet(workingSet);
					}
