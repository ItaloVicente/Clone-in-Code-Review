		ObjectId[] tmpIds = new ObjectId[newParents.size()];

		int newParentCount = 0;
		for (AnyObjectId newId : newParents) {
			for (int j = 0; j < newParentCount; j++)
				if (tmpIds[j].equals(newId))
					throw new IllegalArgumentException(MessageFormat.format(
							JGitText.get().duplicateParents,
							tmpIds[j].getName()));
			tmpIds[newParentCount++] = newId.copy();
		}
		parentIds = tmpIds;
