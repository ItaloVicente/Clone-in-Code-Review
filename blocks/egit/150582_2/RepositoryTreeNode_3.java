	private int compareGroupHideableState(RepositoryTreeNode other) {
		boolean meHidden = ((RepositoryGroupNode) this).getGroup().isHideable();
		boolean otherHidden = ((RepositoryGroupNode) other).getGroup()
				.isHideable();
		if (meHidden == otherHidden) {
			return 0;
		} else if (meHidden) {
			return 1;
		} else {
			return -1;
		}
	}

