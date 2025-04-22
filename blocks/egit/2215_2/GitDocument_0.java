				if (GitTraceLocation.QUICKDIFF.isActive())
					GitTraceLocation
							.getTrace()
							.trace(
									GitTraceLocation.QUICKDIFF.getLocation(),
									"(GitDocument) resource " + resource + " not found in " + treeId + " in " + repository + ", baseline=" + baseline); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
