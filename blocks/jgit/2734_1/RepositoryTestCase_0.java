
	protected void createBranch(ObjectId objectId
			throws IOException {
		RefUpdate updateRef = db.updateRef(branchName);
		updateRef.setNewObjectId(objectId);
		updateRef.update();
	}

	protected void checkoutBranch(String branchName)
			throws IllegalStateException
		RevWalk walk = new RevWalk(db);
		RevCommit head = walk.parseCommit(db.resolve(Constants.HEAD));
		RevCommit branch = walk.parseCommit(db.resolve(branchName));
		DirCacheCheckout dco = new DirCacheCheckout(db
				db.lockDirCache()
		dco.setFailOnConflict(true);
		dco.checkout();
		walk.release();
		RefUpdate refUpdate = db.updateRef(Constants.HEAD);
		refUpdate.link(branchName);
	}
