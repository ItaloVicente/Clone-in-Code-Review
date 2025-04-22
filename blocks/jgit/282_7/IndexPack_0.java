	public void setNeedNewObjectIds(boolean b) {
		if (b)
			newObjectIds = new HashSet<ObjectId>();
		else
			newObjectIds = null;
	}

	private boolean needNewObjectIds() {
		return newObjectIds != null;
	}

	public void setNeedBaseObjectIds(boolean b) {
		this.needBaseObjectIds = b;
	}

	public Set<ObjectId> getNewObjectIds() {
		return newObjectIds == null ?
				Collections.<ObjectId>emptySet() : newObjectIds;
	}

	public Set<ObjectId> getBaseObjectIds() {
		return baseIds == null ?
				Collections.<ObjectId>emptySet() : baseIds;
	}

