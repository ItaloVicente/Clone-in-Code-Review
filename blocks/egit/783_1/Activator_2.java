				for (Repository repo : repos) {
					if (monitor.isCanceled())
						break;
					if (GitTraceLocation.UI.isActive())
						GitTraceLocation.getTrace().trace(
								GitTraceLocation.UI.getLocation(),
								"Scanning " + repo + " for changes"); //$NON-NLS-1$ //$NON-NLS-2$

					repo.scanForRepoChanges();
