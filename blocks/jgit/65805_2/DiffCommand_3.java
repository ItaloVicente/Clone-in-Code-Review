	public DiffCommand setOldTree(ObjectId oldTreeish) {
		this.oldTreeish = oldTreeish.copy();
		oldTree = null;
		return this;
	}

