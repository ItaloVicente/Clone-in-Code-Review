	@Override
	public long getUpdateIndex() {
		if (updateIndex == -1) {
			throw new UnsupportedOperationException();
		}
		return updateIndex;
	}

