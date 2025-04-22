		if (focusListener != null) {
			if (PlatformUI.isWorkbenchRunning()) {
				PlatformUI.getWorkbench().removeWindowListener(focusListener);
			}
			focusListener = null;
		}

		if (GitTraceLocation.REPOSITORYCHANGESCANNER.isActive()) {
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.REPOSITORYCHANGESCANNER.getLocation(),
