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
