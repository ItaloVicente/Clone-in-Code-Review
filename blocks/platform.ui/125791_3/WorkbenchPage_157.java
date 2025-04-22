    private IPropertyChangeListener workingSetPropertyChangeListener = new IPropertyChangeListener() {
        @Override
		public void propertyChange(PropertyChangeEvent event) {
            String property = event.getProperty();
            if (IWorkingSetManager.CHANGE_WORKING_SET_REMOVE.equals(property)) {
            		if(event.getOldValue().equals(workingSet)) {
						setWorkingSet(null);
					}

				List<IWorkingSet> newList = new ArrayList<>(Arrays.asList(workingSets));
				if (newList.remove(event.getOldValue())) {
					setWorkingSets(newList.toArray(new IWorkingSet[newList.size()]));
				}
            }
        }
    };
