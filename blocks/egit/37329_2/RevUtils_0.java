	public static ConflictCommits getConflictCommits(Repository repository,
			String path) throws IOException {
		RevWalk walk = new RevWalk(repository);
		try {
			RevCommit ourCommit;
			RevCommit theirCommit = null;
			walk.setTreeFilter(AndTreeFilter.create(PathFilter.create(path),
					TreeFilter.ANY_DIFF));

			RevCommit head = walk.parseCommit(repository
					.resolve(Constants.HEAD));
			walk.markStart(head);

			ourCommit = walk.next();

			RepositoryState state = repository.getRepositoryState();
			if (state == RepositoryState.REBASING
					|| state == RepositoryState.CHERRY_PICKING) {
				ObjectId cherryPickHead = repository.readCherryPickHead();
				if (cherryPickHead != null) {
					RevCommit cherryPickCommit = walk.parseCommit(cherryPickHead);
					theirCommit = cherryPickCommit;
				}
			} else if (state == RepositoryState.MERGING) {
				List<ObjectId> mergeHeads = repository.readMergeHeads();
				if (mergeHeads.size() == 1) {
					ObjectId mergeHead = mergeHeads.get(0);
					RevCommit mergeCommit = walk.parseCommit(mergeHead);
					walk.reset();
					walk.markStart(mergeCommit);
					theirCommit = walk.next();
				}
			}

			return new ConflictCommits(ourCommit, theirCommit);
		} finally {
			walk.release();
		}
	}

