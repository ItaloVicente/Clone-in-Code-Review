	public void setSortOrder(Comparator sorter) {
		Assert.isNotNull(sorter);
		this.sortOrder = sorter;
		sortMon.cancel();
		refresh();
	}

	public void setFilter(IFilter toSet) {
		Assert.isNotNull(toSet);
		this.filter = toSet;
		sortMon.cancel();
		refresh();
	}

	public void setLimit(int limit) {
		this.limit = limit;
		refresh();
	}

	public int getLimit() {
		return limit;
	}

	public void checkVisibleRange(int includeIndex) {
		updator.checkVisibleRange(includeIndex);
