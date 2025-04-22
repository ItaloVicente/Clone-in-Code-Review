	private RevCommit transform(RevCommit c) {
		if (c == null) {
			return null;
		}

		if (c instanceof FilteredRevCommit) {
			return c;
		}

		if (!transformedCommits.containsKey(c)) {
			RevCommit t = new FilteredRevCommit(c
			transformedCommits.put(c
		}

		return transformedCommits.get(c);
	}

	private RevCommit updateParent(RevCommit c
		if (!(c instanceof FilteredRevCommit)) {
			c = transform(c);
		}

		((FilteredRevCommit) c).setParents(parents);

		return c;
	}

