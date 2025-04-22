		return new MaxCountRevFilter(maxCount
				and == null ? null : and.clone());
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return "(" + (and == null ? "" : and.toString()) + " FILTER_MAX_COUNT "
				+ maxCount + ")";
