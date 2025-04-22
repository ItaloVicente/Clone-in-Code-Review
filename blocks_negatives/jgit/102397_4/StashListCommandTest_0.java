		RefUpdate create = db.updateRef(Constants.R_STASH);
		create.setNewObjectId(commit1);
		assertEquals(Result.NEW, create.update());

		RefUpdate update = db.updateRef(Constants.R_STASH);
		update.setNewObjectId(commit2);
		assertEquals(Result.FAST_FORWARD, update.update());
