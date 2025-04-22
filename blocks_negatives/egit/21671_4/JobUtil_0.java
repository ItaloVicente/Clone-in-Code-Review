			final Object jobFamily, IJobChangeListener jobChangeListener) {
		Job job = new Job(jobName) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					op.execute(monitor);
				} catch (CoreException e) {
					return e.getStatus();
