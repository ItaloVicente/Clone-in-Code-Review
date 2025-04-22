		if (!db.equals(currentRepo)) {
			repoChanged = true;
			currentRepo = db;
		}

		return pathChanged || headChanged || fetchHeadChanged || repoChanged
