	public static RevCommit getTheirs(Repository repository)
			throws IOException {
		try (RevWalk walk = new RevWalk(repository)) {
			RepositoryState state = repository.getRepositoryState();
			if (state == RepositoryState.REBASING
					|| state == RepositoryState.CHERRY_PICKING) {
				ObjectId cherryPickHead = repository.readCherryPickHead();
				if (cherryPickHead != null) {
					RevCommit cherryPickCommit = walk
							.parseCommit(cherryPickHead);
					return cherryPickCommit;
				}
			} else if (state == RepositoryState.MERGING) {
				List<ObjectId> mergeHeads = repository.readMergeHeads();
				Assert.isNotNull(mergeHeads);
				if (mergeHeads.size() == 1) {
					ObjectId mergeHead = mergeHeads.get(0);
					return walk.parseCommit(mergeHead);
				}
			}
			return null;
		}
	}

