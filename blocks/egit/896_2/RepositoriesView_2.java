				boolean actTrace = GitTraceLocation.REPOSITORIESVIEW.isActive();
				if (actTrace)
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.REPOSITORIESVIEW.getLocation(),
							"Running the update"); //$NON-NLS-1$
				lastInputUpdate = System.currentTimeMillis();
