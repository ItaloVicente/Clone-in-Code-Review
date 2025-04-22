	@SuppressWarnings("unused")
	void stopWalk() throws CorruptObjectException {
		for (AbstractTreeIterator t : trees) {
			t.stopWalk();
		}
	}

