		if (extensionStateModel.getBooleanProperty(WorkingSetsContentProvider.SHOW_TOP_LEVEL_WORKING_SETS)
		    && extensionStateModel.getBooleanProperty(WorkingSetsContentProvider.SHOW_OTHERS_WORKING_SET)) {
			workingSetFilter.setWorkingSet(null);
		} else {
			workingSetFilter.setWorkingSet(emptyWorkingSet ? null : workingSet);
		}
