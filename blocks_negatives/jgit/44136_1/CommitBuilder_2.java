		ObjectId[] tmpIds = new ObjectId[newParents.length];

		int newParentCount = 0;
		for (int i = 0; i < newParents.length; i++) {
			for (int j = 0; j < newParentCount; j++)
				if (tmpIds[j].equals(newParents[i]))
					throw new IllegalArgumentException(MessageFormat.format(
							JGitText.get().duplicateParents,
							tmpIds[j].getName()));
			tmpIds[newParentCount++] = newParents[i].copy();
		}
		parentIds = tmpIds;
