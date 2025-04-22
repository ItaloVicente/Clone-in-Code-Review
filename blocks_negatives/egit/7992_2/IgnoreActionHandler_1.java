		final IgnoreOperation operation = new IgnoreOperation(paths);
		String jobname = UIText.IgnoreActionHandler_addToGitignore;
		Job job = new Job(jobname) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					operation.execute(monitor);
				} catch (CoreException e) {
					return Activator.createErrorStatus(e.getStatus()
							.getMessage(), e);
				}
				if (operation.isGitignoreOutsideWSChanged())
					GitLightweightDecorator.refresh();
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.setRule(operation.getSchedulingRule());
		job.schedule();
