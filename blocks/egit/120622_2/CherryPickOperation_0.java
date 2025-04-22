	public void setMainlineIndex(int parentIndex) {
		if (parentIndex >= 0 && parentIndex < commit.getParentCount()) {
			this.parentIndex = parentIndex;
		}
	}

