			if (forceNewWalk || pathChange(pathFilters, paths)
					|| currentWalk == null || !headId.equals(currentHeadId)) {
				createNewWalk(db, headId);
			} else {
				currentWalk.reset();
			}
			setWalkStartPoints(db, headId);
