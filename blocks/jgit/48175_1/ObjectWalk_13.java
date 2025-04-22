	public ObjectFilter getObjectFilter() {
		return objectFilter;
	}

	public void setObjectFilter(ObjectFilter newFilter) {
		assertNotStarted();
		objectFilter = newFilter != null ? newFilter : ObjectFilter.ALL;
	}

