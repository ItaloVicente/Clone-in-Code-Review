	public void addParentId(AnyObjectId additionalParent) {
		if (parentIds.length == 0) {
			setParentId(additionalParent);
		} else {
			ObjectId[] newParents = new ObjectId[parentIds.length + 1];
			for (int i = 0; i < parentIds.length; i++)
				newParents[i] = parentIds[i];
			newParents[parentIds.length] = additionalParent.copy();
			parentIds = newParents;
			commitId = null;
