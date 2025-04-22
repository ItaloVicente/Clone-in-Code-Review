						long start = 0;
						boolean traceActive = GitTraceLocation.REPOSITORIESVIEW
								.isActive();
						if (traceActive) {
							start = System.currentTimeMillis();
							GitTraceLocation.getTrace().trace(
									GitTraceLocation.REPOSITORIESVIEW
											.getLocation(),
									"Starting async update job"); //$NON-NLS-1$
						}
