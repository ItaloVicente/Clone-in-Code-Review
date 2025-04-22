
		IPath[] locations = getSelectedLocations(selection);
		if (GitTraceLocation.SELECTION.isActive())
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.SELECTION.getLocation(), "selection=" //$NON-NLS-1$
							+ selection + ", locations=" //$NON-NLS-1$
							+ Arrays.toString(locations));

		for (IPath location : locations) {
