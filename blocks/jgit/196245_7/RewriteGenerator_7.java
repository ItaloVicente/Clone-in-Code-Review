
		if (c instanceof FilteredRevCommit) {
			return (FilteredRevCommit) c;
		}

		if (!transformedCommits.containsKey(c)) {
			transformedCommits.put(c
		}

		return transformedCommits.get(c);
