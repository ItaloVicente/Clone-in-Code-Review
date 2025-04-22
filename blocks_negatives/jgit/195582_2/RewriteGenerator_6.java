
		if (c instanceof FilteredRevCommit) {
			return (FilteredRevCommit) c;
		}

		if (!transformedCommits.containsKey(c)) {
			transformedCommits.put(c, new FilteredRevCommit(c, c.getParents()));
		}

		return transformedCommits.get(c);
