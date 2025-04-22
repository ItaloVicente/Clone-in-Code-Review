		if (WorkbenchActivityHelper.filterItem(element)) {
			setHasEncounteredFilteredItem(true);
			return false;
		}
		return true;
	}
