		final IgnoreOperation operation = new IgnoreOperation(Arrays.asList(paths));
		Job job = new Job("Ignoring resources for test") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					operation.execute(monitor);
				} catch (CoreException e) {
					return e.getStatus();
				}
				return Status.OK_STATUS;
			}
		};
		job.setRule(operation.getSchedulingRule());
		job.schedule();
		job.join();
		if (!job.getResult().isOK())
			fail("Ignore job failed: " + job.getResult());
