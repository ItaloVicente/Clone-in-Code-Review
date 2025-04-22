	static class JobUISynchronizeImpl extends UISynchronize {
		@Override
		public void syncExec(Runnable runnable) {
			runnable.run();
		}

		@Override
		public void asyncExec(final Runnable runnable) {
			Job job = new Job("") {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					runnable.run();
					return Status.OK_STATUS;
				}
			};
			job.setPriority(Job.INTERACTIVE);
			job.schedule();
		}
	}
