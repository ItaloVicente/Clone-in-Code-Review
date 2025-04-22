	private int getFirstNonEofTreeIndex() {
		for (int i = 0; i < trees.length; i++) {
			if (!trees[i].eof()) {
				return i;
			}
		}
		return -1;
	}

