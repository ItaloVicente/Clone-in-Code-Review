	public void dispose() {
		trace = GitTraceLocation.HISTORYVIEW.isActive();
		if (trace)
			GitTraceLocation.getTrace().traceEntry(
					GitTraceLocation.HISTORYVIEW.getLocation());

		if (myRefsChangedHandle != null) {
			myRefsChangedHandle.remove();
			myRefsChangedHandle = null;
