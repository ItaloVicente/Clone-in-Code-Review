	@SuppressWarnings("unused")
	void stopWalk() throws IOException {
		for (AbstractTreeIterator t : trees) {
			t.stopWalk();
		}
	}

