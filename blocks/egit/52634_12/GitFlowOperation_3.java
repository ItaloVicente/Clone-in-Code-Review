	private boolean hasMultipleCommits(String branchName) throws IOException {
		return getAheadOfDevelopCount(branchName) > 1;
	}

	private int getAheadOfDevelopCount(String branchName) throws IOException {
		String parentBranch = repository.getConfig().getDevelop();

		Ref develop = repository.findBranch(parentBranch);
		Ref branch = repository.findBranch(branchName);

		RevWalk walk = new RevWalk(repository.getRepository());

		RevCommit branchCommit = walk.parseCommit(branch.getObjectId());
		RevCommit developCommit = walk.parseCommit(develop.getObjectId());

		RevCommit mergeBase = findCommonBase(walk, branchCommit, developCommit);

		walk.reset();
		walk.setRevFilter(RevFilter.ALL);
		int aheadCount = RevWalkUtils.count(walk, branchCommit, mergeBase);

		return aheadCount;
	}

	private RevCommit findCommonBase(RevWalk walk, RevCommit branchCommit,
			RevCommit developCommit) throws IOException {
		walk.setRevFilter(RevFilter.MERGE_BASE);
		walk.markStart(branchCommit);
		walk.markStart(developCommit);
		return walk.next();
	}

