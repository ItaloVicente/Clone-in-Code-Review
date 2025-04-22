	private ViewerSorter findApplicableSorter(INavigatorContentDescriptor descriptor, Object parent,
			Object e1,
			Object e2) {
		ViewerSorter sorter = sorterService.findSorter(descriptor, parent, e1, e2);
		if (!descriptor.isSortOnly()) { // for compatibility
			if (!(descriptor.isTriggerPoint(e1) && descriptor.isTriggerPoint(e2))) {
				return null;
			}
		}
		return sorter;
	}

