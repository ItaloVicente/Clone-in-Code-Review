						if (traceActive)
							GitTraceLocation
									.getTrace()
									.trace(
											GitTraceLocation.REPOSITORIESVIEW
													.getLocation(),
											"Ending async update job after " + (System.currentTimeMillis() - start) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$
