
	private void checkoutBranch(String branchName)
			throws IllegalStateException, IOException {
		RevWalk walk = new RevWalk(db);
		RevCommit head = walk.parseCommit(db.resolve(Constants.HEAD));
		RevCommit branch = walk.parseCommit(db.resolve(branchName));
		DirCacheCheckout dco = new DirCacheCheckout(db, head.getTree(),
				db.lockDirCache(), branch.getTree());
		dco.setFailOnConflict(true);
		assertTrue(dco.checkout());
		walk.release();
		RefUpdate refUpdate = db.updateRef(Constants.HEAD);
		assertEquals(Result.FORCED, refUpdate.link(branchName));
	}

