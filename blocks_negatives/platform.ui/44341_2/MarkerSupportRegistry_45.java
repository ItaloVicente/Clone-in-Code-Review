					IStatus status = new Status(
							IStatus.WARNING,
							IDEWorkbenchPlugin.IDE_WORKBENCH,
							IStatus.WARNING,
							NLS
									.bind(
											MarkerMessages.ProblemFilterRegistry_nullType,
											new Object[] { markerId,
													filter.getName() }), null);
