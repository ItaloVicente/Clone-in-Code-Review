	private void checkoutBranch(String branchName) throws IllegalStateException
		RevWalk walk = new RevWalk(db);
		RevCommit head = walk.parseCommit(db.resolve(Constants.HEAD));
		RevCommit branch = walk.parseCommit(db.resolve(branchName));
		DirCacheCheckout dco = new DirCacheCheckout(db
				head.getTree().getId()
				branch.getTree().getId());
		dco.setFailOnConflict(true);
		dco.checkout();
		walk.release();
