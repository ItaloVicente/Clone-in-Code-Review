								GitTraceLocation.REPOSITORYCHANGESCANNER
										.getLocation(),
								"Stopped rescheduling " + getName() + " job"); //$NON-NLS-1$ //$NON-NLS-2$
					return createErrorStatus(UIText.Activator_scanError, e);
				} finally {
					monitor.done();
