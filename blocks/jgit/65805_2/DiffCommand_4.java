		oldTreeish = null;
		return this;
	}

	public DiffCommand setNewTree(ObjectId newTreeish) {
		this.newTreeish = newTreeish.copy();
		newTree = null;
