	@Deprecated
	public final void setFilterBlobLimit(long bytes) {
		setFilterSpec(FilterSpec.withBlobLimit(bytes));
	}

	public final FilterSpec getFilterSpec() {
		return filterSpec;
	}

	public final void setFilterSpec(@NonNull FilterSpec filter) {
		filterSpec = requireNonNull(filter);
