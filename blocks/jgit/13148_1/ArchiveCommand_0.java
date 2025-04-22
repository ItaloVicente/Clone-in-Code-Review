	public ArchiveCommand setTree(ObjectId tree) {
		if (tree == null)
			throw new IllegalArgumentException();

		this.tree = tree;
		setCallable(true);
