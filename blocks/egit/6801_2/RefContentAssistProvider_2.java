								public void run(IProgressMonitor monitor)
										throws InvocationTargetException,
										InterruptedException {
									monitor
											.beginTask(
													UIText.RefSpecDialog_GettingRemoteRefsMonitorMessage,
													IProgressMonitor.UNKNOWN);
									fetchPushDestinationRefsForContentAssist().join();
									monitor.done();
								}
							});
				}
				IStatus jobResult = fetchJob.getResult();
				if (jobResult != null && jobResult.matches(IStatus.ERROR)) {
					Throwable e = jobResult.getException();
					if (e instanceof RuntimeException) {
						throw (RuntimeException) e;
					} else if (e instanceof Exception) {
						Activator.handleError(e.getMessage(), e, true);
						return result;
					}
				}
				result = fetchJob.result;
