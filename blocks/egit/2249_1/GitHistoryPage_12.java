	void initAndStartRevWalk(boolean forceNewWalk) throws IllegalStateException {
		try {
			if (trace)
				GitTraceLocation.getTrace().traceEntry(
						GitTraceLocation.HISTORYVIEW.getLocation());
	
			cancelRefreshJob();
			Repository db = input.getRepository();
			AnyObjectId headId = resolveHead(db);
	
			List<String> paths = buildFilterPaths(input.getItems(), input
					.getFileList(), db);
	
			if (forceNewWalk || pathChange(pathFilters, paths)
					|| currentWalk == null || !headId.equals(currentHeadId)) {
				createNewWalk(db, headId);
			} else {
				currentWalk.reset();
			}
			setWalkStartPoints(db, headId);
	
			final TreeWalk fileWalker = setupFileViewer(db, paths);
			setupCommentViewer(db, fileWalker);
	
			scheduleNewGenerateHistoryJob();
		} finally {
			if (trace)
				GitTraceLocation.getTrace().traceExit(
						GitTraceLocation.HISTORYVIEW.getLocation());
	
		}
