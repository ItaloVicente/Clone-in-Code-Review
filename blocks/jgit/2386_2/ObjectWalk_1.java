	public byte[] getPathBuffer() {
		return last != null ? treeWalk.getEntryPathBuffer() : EMPTY_PATH;
	}

	public int getPathLength() {
		return last != null ? treeWalk.getEntryPathLength() : 0;
	}

