
	public void setFilter(List<String> filter) {
		myFilter = filter;
	}

	public List<String> getFilter() {
		if (myFilter == null) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(myFilter);
	}
