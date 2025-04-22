		}
		return list;
	}

	protected int indexForElement(Object element) {
		ViewerComparator comparator = getComparator();
		if (comparator == null) {
