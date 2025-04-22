
	@Override
	public ResourceComparator getComparator() {
		ViewerComparator comparator = getTreeViewer().getComparator();
		if (comparator instanceof ResourceComparator) {
			return (ResourceComparator) comparator;
		}
		return null;
	}

	@Override
