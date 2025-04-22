		try {
			trace = GitTraceLocation.HISTORYVIEW.isActive();
			if (trace)
				GitTraceLocation.getTrace().traceEntry(
						GitTraceLocation.HISTORYVIEW.getLocation(), object);

			if (object == getInput())
				return true;
			this.input = null;
			return super.setInput(object);
		} finally {
			if (trace)
				GitTraceLocation.getTrace().traceExit(
						GitTraceLocation.HISTORYVIEW.getLocation());
		}
