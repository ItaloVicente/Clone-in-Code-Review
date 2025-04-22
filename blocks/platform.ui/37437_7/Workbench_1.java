						new EarlyStartupJob(jobGroup) {
							@Override
							protected IStatus run(IProgressMonitor monitor) {
								monitor.beginTask(extension.getNamespaceIdentifier(), 1);
								if (monitor.isCanceled()) {
									return Status.CANCEL_STATUS;
								}
								SafeRunner.run(new EarlyStartupRunnable(extension));
								monitor.done();
								return Status.OK_STATUS;
							}
						}.schedule();
