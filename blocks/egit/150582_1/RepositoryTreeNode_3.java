	private Optional<Integer> compareToOtherGroup(RepositoryTreeNode other) {
		boolean meHidden = ((RepositoryGroupNode) this).getGroup().isHidden();
		boolean otherHidden = ((RepositoryGroupNode) other).getGroup()
				.isHidden();
		if (meHidden == otherHidden) {
			return Optional.empty();
		} else if (meHidden) {
			return Optional.of(Integer.valueOf(1));
		} else {
			return Optional.of(Integer.valueOf(-1));
		}
	}

