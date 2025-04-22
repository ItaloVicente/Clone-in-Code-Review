				if (lastInputChange > lastInputUpdate
						|| lastRepositoryChange > lastInputUpdate) {
					if (actTrace)
						GitTraceLocation.getTrace()
								.trace(
										GitTraceLocation.REPOSITORIESVIEW
												.getLocation(),
										"Rescheduling refresh job"); //$NON-NLS-1$
					schedule(DEFAULT_REFRESH_DELAY);
				}
