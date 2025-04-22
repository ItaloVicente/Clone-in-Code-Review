				AnyObjectId fetchHeadId = resolveFetchHead(db);

				List<FilterPath> paths = buildFilterPaths(input.getItems(),
						input.getFileList(), db);

				boolean repoChanged = false;
				if (!db.equals(getCurrentRepo())) {
					repoChanged = true;
					setCurrentRepo(db);
				}

				boolean objChanged = false;
				if (newSelectedObj != null && newSelectedObj != selectedObj) {
					objChanged = !newSelectedObj.equals(selectedObj);
				}
				selectedObj = newSelectedObj;

				boolean headChanged = !headId.equals(currentHeadId);
				boolean pathsChanged = pathChanged(pathFilters, paths);
				boolean settingsChanged = updateSettings();
				boolean fetchHeadChanged = currentShowAdditionalRefs
						&& fetchHeadId != null
						&& !fetchHeadId.equals(currentFetchHeadId);
				if (forceNewWalk || repoChanged || objChanged || headChanged
						|| fetchHeadChanged || pathsChanged
						|| settingsChanged) {
					releaseGenerateHistoryJob();

					if (repoChanged) {
						clearViewers();
					}
