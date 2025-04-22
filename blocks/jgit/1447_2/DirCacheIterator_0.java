	@Override
	public boolean isSkipped() {
		return currentSubtree == null && currentEntry != null
				&& currentEntry.isSkipWorkTree();
	}

