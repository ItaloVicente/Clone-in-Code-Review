    private IPropertyChangeListener workingSetPropertyChangeListener = new IPropertyChangeListener() {
        /*
         * Remove the working set from the page if the working set is deleted.
         */
        @Override
		public void propertyChange(PropertyChangeEvent event) {
            String property = event.getProperty();
            if (IWorkingSetManager.CHANGE_WORKING_SET_REMOVE.equals(property)) {
            		if(event.getOldValue().equals(workingSet)) {
						setWorkingSet(null);
					}
