		final RevWalk walk = new RevWalk(objectReader);
		walk.setRetainBody(true);
		for (ObjectId merge : merges)
			try {
				commits.add(new RepositoryCommit(repository, walk
						.parseCommit(merge)));
			} catch (IOException e) {
				Activator.logError(MessageFormat.format(
						UIText.MergeResultDialog_couldNotFindCommit,
						merge.name()), e);
			}
		return commits.toArray(new RepositoryCommit[commits.size()]);
