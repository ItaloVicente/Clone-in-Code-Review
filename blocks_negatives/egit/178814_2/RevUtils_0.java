	/**
	 * @param repository
	 * @param path
	 *            repository-relative path of file with conflicts
	 * @return an object with the interesting commits for this path
	 * @throws IOException
	 */
	public static ConflictCommits getConflictCommits(Repository repository,
			String path) throws IOException {
		try (RevWalk walk = new RevWalk(repository)) {
			RevCommit ourCommit;
			RevCommit theirCommit = null;
			walk.setTreeFilter(AndTreeFilter.create(
					PathFilterGroup.createFromStrings(path),
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
				Assert.isNotNull(mergeHeads);
				if (mergeHeads.size() == 1) {
					ObjectId mergeHead = mergeHeads.get(0);
					RevCommit mergeCommit = walk.parseCommit(mergeHead);
					walk.reset();
					walk.markStart(mergeCommit);
					theirCommit = walk.next();
				}
			}

			return new ConflictCommits(ourCommit, theirCommit);
		}
	}

