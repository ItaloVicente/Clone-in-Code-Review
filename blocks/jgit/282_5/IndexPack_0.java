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

