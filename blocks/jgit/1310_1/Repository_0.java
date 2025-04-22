	public GitIndex getIndex(Tree newTree) throws IOException {
		if (isBare())
			throw new NoWorkTreeException();
		if (index == null) {
			index = new GitIndex(this);
		}
		index.readTree(newTree);
		return index;
	}

