	private AbstractTreeIterator resolveOldTree(RevWalk rw)
			throws NoHeadException
		if (oldTree != null) {
			return oldTree;
		} else if (oldTreeish != null) {
			return getTreeIterator(rw
		} else if (cached) {
			if (head == null) {
				throw new NoHeadException(JGitText.get().cannotReadTree);
