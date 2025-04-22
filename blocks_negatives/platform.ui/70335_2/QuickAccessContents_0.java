	int numberOfFilteredResults;

	/**
	 * Compute how many items are effectively filtered at a specific point in
	 * time. So doing, the quick access content can perform operations that
	 * depends on this number, i.e. hide the info label.
	 *
	 * @return number number of elements filtered
	 */
	protected int getNumberOfFilteredResults() {
		return numberOfFilteredResults;
	}

