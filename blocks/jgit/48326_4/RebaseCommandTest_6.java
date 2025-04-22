		RevCommit head;
		try (RevWalk walk = new RevWalk(db)) {
			head = walk.parseCommit(db.resolve(Constants.HEAD));
			DirCacheCheckout dco = new DirCacheCheckout(db
					db.lockDirCache()
			dco.setFailOnConflict(true);
			dco.checkout();
		}
