	public void setShallowPack(int depth
			Collection<? extends ObjectId> unshallow) {
		this.shallowPack = true;
		this.depth = depth;
		this.unshallowObjects = unshallow;
	}

