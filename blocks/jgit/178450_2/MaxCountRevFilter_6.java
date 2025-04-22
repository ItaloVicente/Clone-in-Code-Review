		if (and == null) {
			return new MaxCountRevFilter(maxCount
		} else {
			return new MaxCountRevFilter(maxCount
		}
	}

	@Override
	public String toString() {
		return "(" + and.toString() + " FILTER_MAX_COUNT " + maxCount + ")";
