	public void clearFilter() {
		getPatternFilter().setPattern(null);
		setFilterText(getInitialText());
		textChanged();
	}

