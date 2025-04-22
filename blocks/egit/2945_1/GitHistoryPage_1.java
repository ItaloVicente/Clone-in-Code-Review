			if (repoHasBeenRemoved(db)) {
				clearHistoryPage();
				return;
			}

			AnyObjectId headId = resolveHead(db, true);
			if (headId == null)
				return;
