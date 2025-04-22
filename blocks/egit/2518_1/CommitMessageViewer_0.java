		final boolean trace = GitTraceLocation.HISTORYVIEW.isActive();
		if (trace)
			GitTraceLocation.getTrace().traceEntry(
					GitTraceLocation.HISTORYVIEW.getLocation());

		final List<StyleRange> styles = new ArrayList<StyleRange>();
