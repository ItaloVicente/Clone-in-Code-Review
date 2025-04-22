	public boolean isMarked(int index) {
		return (treeFilterMarks & (1L << index)) != 0;
	}

	public int getTreeFilterMarks() {
		return treeFilterMarks;
	}

