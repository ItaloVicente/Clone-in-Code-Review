
		if (c instanceof FilteredRevCommit) {
			return (FilteredRevCommit) c;
		}

		if (!transformedCommits.contains(c)) {
			transformedCommits.add(new FilteredRevCommit(c
		}

		return transformedCommits.get(c);
