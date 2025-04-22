	@Deprecated
	public final void setFilterBlobLimit(long bytes) {
		setFilterSpec(FilterSpec.withBlobLimit(bytes));
	}

	public final FilterSpec getFilterSpec() {
		return filterSpec;
	}

	public final void setFilterSpec(FilterSpec filter) {
		if (filter == null) {
			throw new NullPointerException();
		}
		filterSpec = filter;
