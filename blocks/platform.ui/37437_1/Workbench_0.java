
						Job extensionJob = new Job("Early stattup job") { //$NON-NLS-1$
							@Override
							protected IStatus run(IProgressMonitor monitor) {
								monitor.beginTask(extension.getNamespaceIdentifier(), 1);
								SafeRunner.run(new EarlyStartupRunnable(extension));
								monitor.done();
								return Status.OK_STATUS;
							}

							@Override
							public boolean belongsTo(Object family) {
								return EARLY_STARTUP_FAMILY.equals(family);
							}
						};

						extensionJob.setSystem(true);
						extensionJob.setJobGroup(jobGroup);
						extensionJob.schedule();
