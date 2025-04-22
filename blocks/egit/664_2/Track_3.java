			String jobname = UIText.Track_addToVersionControl;
			Job job = new Job(jobname) {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try {
						op.execute(monitor);
					} catch (CoreException e) {
						return Activator.createErrorStatus(e.getStatus()
								.getMessage(), e);
					}
					return Status.OK_STATUS;
				}
			};
			job.setUser(true);
			job.schedule();
