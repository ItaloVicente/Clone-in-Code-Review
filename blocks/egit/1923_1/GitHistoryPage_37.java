		myRefsChangedHandle = Repository.getGlobalListenerList()
				.addRefsChangedListener(this);

		if (trace)
			GitTraceLocation.getTrace().traceExit(
					GitTraceLocation.HISTORYVIEW.getLocation());
