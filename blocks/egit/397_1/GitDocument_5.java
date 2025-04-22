			if (GitTraceLocation.UI.isActive())
				GitTraceLocation
						.getTrace()
						.trace(
								GitTraceLocation.UI.getLocation(),
								"(GitDocument) resolved " + resource + " to " + lastBlob + " in " + lastCommit + "/" + lastTree); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			else
			if (GitTraceLocation.UI.isActive())
				GitTraceLocation.getTrace().trace(
						GitTraceLocation.UI.getLocation(),
						"(GitDocument) unresolved " + resource); //$NON-NLS-1$
