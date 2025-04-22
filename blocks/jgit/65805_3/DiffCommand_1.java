	private AbstractTreeIterator resolveOldTree(RevWalk rw)
			throws NoHeadException
		if (oldTree != null && oldTreeish != null) {
			throw new AssertionError();
		} else if (oldTree != null) {
			return oldTree;
		} else if (oldTreeish != null) {
			return getTreeIterator(rw
		} else if (cached) {
			if (head == null) {
				throw new NoHeadException(JGitText.get().cannotReadTree);
