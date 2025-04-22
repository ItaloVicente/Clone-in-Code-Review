	private static void updateParents(RevCommit c
		if (c instanceof FilteredRevCommit) {
			((FilteredRevCommit) c).setParents(parents);
		} else {
			c.parents = parents;
		}
	}

	private RevCommit rewrite(RevCommit c) throws MissingObjectException
