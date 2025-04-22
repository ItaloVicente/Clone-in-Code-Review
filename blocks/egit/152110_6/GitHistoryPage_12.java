			UnitOfWork.execute(db, () -> {
				AnyObjectId headId = resolveHead(db, true);
				if (headId == null) {
					currentHeadId = null;
					currentFetchHeadId = null;
					selectedObj = null;
					setCurrentRepo(db);
