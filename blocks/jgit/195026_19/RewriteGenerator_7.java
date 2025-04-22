
		return transformedCommits.get(c);
	}

	private RevCommit updateParent(RevCommit c
		((FilteredRevCommit) c).setParents(parents);
		return c;
