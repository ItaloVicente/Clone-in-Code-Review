		Ref target = headRef.getTarget();
		if (target.getName().equals(headRef.getName())) {
			ObjectId objectId = target.getObjectId();
			if (objectId != null) {
				return objectId.getName();
			}
		}
		return Repository.shortenRefName(target.getName());
