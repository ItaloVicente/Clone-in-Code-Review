			try {
				initAndStartRevWalk(true);
			} catch (IllegalStateException e) {
				Activator.handleError(e.getMessage(), e.getCause(), true);
				return false;
			}
			return true;
		} finally {
			if (trace)
				GitTraceLocation.getTrace().traceExit(
						GitTraceLocation.HISTORYVIEW.getLocation());
