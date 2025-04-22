		this.addToIndex = false;
	}

	BitmapCommit(AnyObjectId objectId
				 boolean addToIndex) {
		super(objectId);
		this.reuseWalker = reuseWalker;
		this.flags = flags;
		this.addToIndex = addToIndex;
