		if (input == null)
			return;
		Repository db = input.getRepository();
		if (repoHasBeenRemoved(db)) {
			clearHistoryPage();
			return;
		}
		try {
			UnitOfWork.execute(db, () -> {
				AnyObjectId headId = resolveHead(db, true);
				if (headId == null) {
					currentHeadId = null;
					currentFetchHeadId = null;
					selectedObj = null;
					setCurrentRepo(db);
