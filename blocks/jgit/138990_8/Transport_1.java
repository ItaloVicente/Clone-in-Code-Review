		filterSpec = FilterSpec.blobFilter(bytes);
	}

	public FilterSpec getFilterSpec() {
		return filterSpec;
	}

	public void setFilterSpec(FilterSpec filter) {
		if (filter == null) {
			throw new NullPointerException();
		}
		filterSpec = filter;
