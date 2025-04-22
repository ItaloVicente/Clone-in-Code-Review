
		if (!transformedCommits.containsKey(c)) {
			transformedCommits.put(c
		}

		return transformedCommits.get(c);
	}

	private RevCommit updateParent(RevCommit c
		c = transform(c);

		((FilteredRevCommit) c).setParents(parents);

		return c;
