		if (refreshHandle != null) {
			refreshHandle.remove();
			refreshHandle = null;
		}
		if (GitTraceLocation.REFRESH.isActive()) {
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.REFRESH.getLocation(),
					"Trying to cancel " + refreshJob.getName() + " job"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		refreshJob.cancel();
		refreshJob.join();

