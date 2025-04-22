		RevWalk walk = new RevWalk(db);
		RevCommit head = walk.parseCommit(db.resolve(Constants.HEAD));
		DirCacheCheckout dco = new DirCacheCheckout(db, head.getTree(), db
				.lockDirCache(), commit.getTree());
		dco.setFailOnConflict(true);
		dco.checkout();
		walk.release();
		RefUpdate refUpdate = db.updateRef(Constants.HEAD, true);
		refUpdate.setNewObjectId(commit);
		refUpdate.setRefLogMessage("checkout: moving to " + head.getName(),
				false);
		refUpdate.forceUpdate();
