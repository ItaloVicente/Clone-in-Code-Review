
	Set<ObjectId> getNewObjectIds() {
		return newObjectIds == null ?
				Collections.<ObjectId>emptySet() : newObjectIds;
	}

	Set<ObjectId> getBaseObjectIds() {
		return baseIds == null ?
				Collections.<ObjectId>emptySet() : baseIds;
	}

