		boolean workingTreeDirty = workingTreeIter != null
				&& (headIter == null || !isEqualEntry(workingTreeIter

		if (indexDirty && stashIndexIter != null && indexIter != null
				&& !isEqualEntry(stashIndexIter
			return true;
