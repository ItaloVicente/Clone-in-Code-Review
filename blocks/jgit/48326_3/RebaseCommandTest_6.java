		try (RevWalk walk = new RevWalk(db)) {
			RevCommit head = walk.parseCommit(db.resolve(Constants.HEAD));
			DirCacheCheckout dco = new DirCacheCheckout(db
					db.lockDirCache()
			dco.setFailOnConflict(true);
			dco.checkout();
			RefUpdate refUpdate = db.updateRef(Constants.HEAD
			refUpdate.setNewObjectId(commit);
			refUpdate.setRefLogMessage("checkout: moving to " + head.getName()
					false);
			refUpdate.forceUpdate();
		}
