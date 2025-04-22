		boolean first = true;
		StringBuilder commits = new StringBuilder();
		for (ObjectId commit : mergedCommits) {
			if (!first)
				commits.append("
			else
				first = false;
			commits.append(ObjectId.toString(commit));
		}
