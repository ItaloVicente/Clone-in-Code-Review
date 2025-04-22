		if (workingTreeDirty && stashWorkingTreeIter != null
				&& workingTreeIter != null
				&& !isEqualEntry(stashWorkingTreeIter
			return true;

		return false;
	}

	private ObjectId getHeadTree() throws JGitInternalException
			GitAPIException {
		final ObjectId headTree;
		try {
			headTree = repo.resolve(Constants.HEAD + "^{tree}");
		} catch (IOException e) {
			throw new JGitInternalException(JGitText.get().cannotReadTree
		}
		if (headTree == null)
			throw new NoHeadException(JGitText.get().cannotReadTree);
		return headTree;
	}

	private ObjectId getStashId() throws JGitInternalException
