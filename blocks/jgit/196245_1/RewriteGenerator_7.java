	private RevCommit transform(RevCommit c) {
		if (c == null) {
			return null;
		}

		if (c instanceof FilteredRevCommit) {
			return c;
		}

		if (!c.getClass().equals(RevCommit.class)) {
			return c;
		}

		if (!transformedCommits.containsKey(c)) {
			transformedCommits.put(c
