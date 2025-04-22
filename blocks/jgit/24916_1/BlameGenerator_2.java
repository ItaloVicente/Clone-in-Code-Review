	private boolean isAncestor(RevCommit parent
		try {
			return revPool.isMergedInto(parent
		} catch (IOException e) {
			return false;
		}
	}

