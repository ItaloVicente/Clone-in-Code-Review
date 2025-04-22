		RevWalk walk = new RevWalk(db);
		RevCommit head = walk.parseCommit(db.resolve(Constants.HEAD));
		DirCacheCheckout dco = new DirCacheCheckout(db, head.getTree(), db
				.lockDirCache(), commit.getTree());
		dco.setFailOnConflict(true);
		dco.checkout();
		walk.release();
