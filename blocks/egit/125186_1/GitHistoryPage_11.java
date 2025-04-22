			boolean repoChanged = false;
			if (!db.equals(currentRepo)) {
				repoChanged = true;
				currentRepo = db;
			}

			if (forceNewWalk || repoChanged
					|| shouldRedraw(headId, fetchHeadId, paths)) {
