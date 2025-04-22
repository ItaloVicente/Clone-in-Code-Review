			throw new InvocationTargetException(e);
		} finally {
			monitor.done();
			if (trace)
				GitTraceLocation.getTrace().traceExit(
						GitTraceLocation.HISTORYVIEW.getLocation());
