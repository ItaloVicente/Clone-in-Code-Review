	public QuickAccessElement[] getElements(String filter, IProgressMonitor monitor) {
		return null;
	}

	public QuickAccessElement[] getElementsSorted(String filter, IProgressMonitor monitor) {
		if (cacheSortedElements == null) {
			cacheSortedElements = getElements();
			Arrays.sort(cacheSortedElements, Comparator.comparing(QuickAccessElement::getSortLabel));
		}
		QuickAccessElement[] filterSpecificElements = getElements(filter, monitor);
		if (filterSpecificElements == null || filterSpecificElements.length == 0) {
			return cacheSortedElements;
