	protected RevCommit getRevCommit(GitSynchronizeData gsd)
			throws TeamException {
		RevCommit result;
		Repository repo = gsd.getRepository();
		RevWalk rw = new RevWalk(repo);
		rw.setRevFilter(RevFilter.MERGE_BASE);
