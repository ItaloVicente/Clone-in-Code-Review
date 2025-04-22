	@Override
	public boolean hasId() {
		if (currentSubtree != null)
			return currentSubtree.isValid();
		return currentEntry != null;
	}

