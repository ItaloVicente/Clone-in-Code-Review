			Job job = new Job(jobname) {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try {
						operation.execute(monitor);
						GitLightweightDecorator.refresh();
					} catch (CoreException e) {
						return Activator.createErrorStatus(e.getStatus()
								.getMessage(), e);
					}
					return Status.OK_STATUS;
				}
			};
			job.setRule(operation.getSchedulingRule());
			job.setUser(true);
			job.schedule();
