
		int newParentCount = 0;
		outer: for (int i = 0; i < newParents.length; i++) {
			for (int j = 0; j < newParentCount; j++)
				if (parentIds[j].equals(newParents[i]))
					continue outer;
			parentIds[newParentCount++] = newParents[i].copy();
		}
		if (newParentCount == parentIds.length)
			return;
		ObjectId[] tmpIds = new ObjectId[newParentCount];
		System.arraycopy(parentIds
		parentIds = tmpIds;
