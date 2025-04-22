				} catch (RuntimeException e) {
					if (GitTraceLocation.INDEXDIFFCACHE.isActive()) {
						GitTraceLocation.getTrace().trace(
								GitTraceLocation.INDEXDIFFCACHE.getLocation(),
								"Calculating IndexDiff failed", e); //$NON-NLS-1$
					}
					scheduleReloadJob();
					return Status.OK_STATUS;
