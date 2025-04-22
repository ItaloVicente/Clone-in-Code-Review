					if (GitTraceLocation.INDEXDIFFCACHE.isActive()) {
						long time = System.currentTimeMillis() - startTime;
						StringBuilder message = new StringBuilder(
								NLS.bind(
										"Updated IndexDiffData based on resource list (length = {0}) in {1} ms\n", //$NON-NLS-1$
										new Integer(fileResourcesToUpdate
												.size()), new Long(time)));
						GitTraceLocation.getTrace().trace(
								GitTraceLocation.INDEXDIFFCACHE.getLocation(),
								message.append(indexDiffData.toString())
								.toString());
					}
