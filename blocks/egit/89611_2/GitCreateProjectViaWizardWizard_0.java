
	public void setFilter(List<String> pFilter) {
		fFilter = pFilter;
	}

	public List<String> getFilter() {
		if (fFilter == null) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(fFilter);
	}
