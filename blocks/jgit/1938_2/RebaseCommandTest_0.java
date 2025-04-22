	private void checkoutCommit(RevCommit commit) throws IllegalStateException
			IOException {
		RevWalk walk = new RevWalk(db);
		RevCommit head = walk.parseCommit(db.resolve(Constants.HEAD));
		DirCacheCheckout dco = new DirCacheCheckout(db
				db.lockDirCache()
		dco.setFailOnConflict(true);
		dco.checkout();
		walk.release();
		RefUpdate refUpdate = db.updateRef(Constants.HEAD
		refUpdate.setNewObjectId(commit);
		refUpdate.forceUpdate();
	}

