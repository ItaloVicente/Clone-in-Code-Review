	public void setShallowPack(int depth
			final Collection<? extends ObjectId> unshallowObjects) {
		this.shallowPack = true;
		this.depth = depth;
		this.unshallowObjects = unshallowObjects;
	}

