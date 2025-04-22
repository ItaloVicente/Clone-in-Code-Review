		RevCommit[] parents = commit.getParents();
		if (parents.length > 1)
			throw new IllegalStateException(

		if (parents.length == 0)
			throw new IllegalStateException(

