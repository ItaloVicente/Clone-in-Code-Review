		if (trace)
			GitTraceLocation.getTrace().traceEntry(
					GitTraceLocation.HISTORYVIEW.getLocation());
		try {
			if (allCommits.size() == lastUpdateCnt)
				return;
