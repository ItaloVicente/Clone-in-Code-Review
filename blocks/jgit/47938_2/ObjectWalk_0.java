	public ObjectFilter getObjectFilter() {
		return objectFilter;
	}

	public void setObjectFilter(final ObjectFilter newFilter) {
		assertNotStarted();
		objectFilter = newFilter != null ? newFilter : ObjectFilter.ALL;
		if (objectFilter != ObjectFilter.ALL) {
			throw new UnsupportedOperationException("not implemented");
		}
	}

