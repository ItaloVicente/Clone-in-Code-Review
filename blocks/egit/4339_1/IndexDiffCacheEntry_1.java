				} catch (RuntimeException e) {
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.INDEXDIFFCACHE.getLocation(),
							"Calculating IndexDiff failed", e); //$NON-NLS-1$
					scheduleReloadJob();
					return Status.OK_STATUS;
