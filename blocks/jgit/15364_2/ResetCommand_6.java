	private String getRefOrHEAD() {
		if (ref != null)
			return ref;
		else
			return Constants.HEAD;
	}

	private void resetIndexForPaths(ObjectId commitTree) {
