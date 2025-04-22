						if (uiIsActive != wasActive
								&& GitTraceLocation.REPOSITORYCHANGESCANNER
										.isActive())
							traceUiIsActive();
					}

					private void traceUiIsActive() {
						StringBuilder message = new StringBuilder(
								"workbench is "); //$NON-NLS-1$
						message.append(uiIsActive ? "active" : "inactive"); //$NON-NLS-1$//$NON-NLS-2$
						GitTraceLocation.getTrace().trace(
								GitTraceLocation.REPOSITORYCHANGESCANNER
										.getLocation(), message.toString());
