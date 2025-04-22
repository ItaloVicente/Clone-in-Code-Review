            String property = event.getProperty();
            if (IWorkingSetManager.CHANGE_WORKING_SET_REMOVE.equals(property)) {
            		if(event.getOldValue().equals(workingSet)) {
						setWorkingSet(null);
					}
            		
