		try {
			if (trace)
				GitTraceLocation.getTrace().traceEntry(
						GitTraceLocation.HISTORYVIEW.getLocation());

			if (input == null) {
				return;
			}
			Repository db = input.getRepository();
			if (repoHasBeenRemoved(db)) {
				clearHistoryPage();
				return;
			}

			AnyObjectId headId = resolveHead(db, true);
			if (headId == null) {
				currentHeadId = null;
				currentFetchHeadId = null;
				selectedObj = null;
				setCurrentRepo(db);
				clearViewers();
				return;
			}
			AnyObjectId fetchHeadId = resolveFetchHead(db);

			List<FilterPath> paths = buildFilterPaths(input.getItems(), input
					.getFileList(), db);

			boolean repoChanged = false;
			if (!db.equals(getCurrentRepo())) {
				repoChanged = true;
				setCurrentRepo(db);
			}
