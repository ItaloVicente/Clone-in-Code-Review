			boolean pathChange = pathChange(pathFilters, paths);
			boolean headChange = !headId.equals(currentHeadId);
			boolean repoChange = false;
			if (!db.equals(currentRepo))
			{
				repoChange = true;
				currentRepo = db;
			}
			if (forceNewWalk || pathChange
					|| currentWalk == null || headChange || repoChange) {
