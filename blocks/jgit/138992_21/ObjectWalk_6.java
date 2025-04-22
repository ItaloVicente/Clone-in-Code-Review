	public int getTreeDepth() {
		if (currVisit == null) {
			return 0;
		}
		return currVisit.depth;
	}

