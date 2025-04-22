					if (GitTraceLocation.INDEXDIFFCACHE.isActive()) {
						long time = System.currentTimeMillis() - startTime;
						StringBuilder message = new StringBuilder(NLS.bind(
								"Updated IndexDiffData in {0} ms\n", //$NON-NLS-1$
								new Long(time)));
						GitTraceLocation.getTrace().trace(
								GitTraceLocation.INDEXDIFFCACHE.getLocation(),
								message.append(indexDiffData.toString())
										.toString());
					}
