				if (GitTraceLocation.CORE.isActive())
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.CORE.getLocation(),
							"Counted " + count[0] //$NON-NLS-1$
									+ " items to update in " //$NON-NLS-1$
									+ (t1 - t0) / 1000.0 + "s"); //$NON-NLS-1$
