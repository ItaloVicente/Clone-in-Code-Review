
		int newParentCount = 0;
		for (AnyObjectId newId : newParents) {
			for (int j = 0; j < newParentCount; j++)
				if (parentIds[j].equals(newId))
					throw new IllegalArgumentException(MessageFormat.format(
							JGitText.get().duplicateParents
							parentIds[j].getName()));
			parentIds[newParentCount++] = newId.copy();
		}
		if (newParentCount == parentIds.length)
			return;
		ObjectId[] tmpIds = new ObjectId[newParentCount];
		System.arraycopy(parentIds
		parentIds = tmpIds;
