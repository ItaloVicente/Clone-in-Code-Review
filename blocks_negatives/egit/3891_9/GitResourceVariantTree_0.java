		ObjectId objectId;
		if (cachedData.getDiffEntry() != null)
			objectId = getObjectId(cachedData.getDiffEntry());
		else
			return null;

