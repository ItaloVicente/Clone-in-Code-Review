
		int newParentCount = 0;
		outer: for (AnyObjectId newId : newParents) {
			for (int j = 0; j < newParentCount; j++)
				if (parentIds[j].equals(newId))
					continue outer;
			parentIds[newParentCount++] = newId.copy();
		}
		if (newParentCount == parentIds.length)
			return;
		ObjectId[] tmpIds = new ObjectId[newParentCount];
		System.arraycopy(parentIds
		parentIds = tmpIds;
