		final CommonViewer tv = getCommonViewer();
		final boolean needsNewInput = lastInputChange > lastInputUpdate;

		if (trace)
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.REPOSITORIESVIEW.getLocation(),
