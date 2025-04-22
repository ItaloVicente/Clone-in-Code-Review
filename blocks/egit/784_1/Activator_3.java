			if (GitTraceLocation.UI.isActive())
				GitTraceLocation.getTrace().trace(
						GitTraceLocation.UI.getLocation(),
						"Rescheduling " + getName() + " job"); //$NON-NLS-1$ //$NON-NLS-2$
			if (doReschedule)
				schedule(REPO_SCAN_INTERVAL);
