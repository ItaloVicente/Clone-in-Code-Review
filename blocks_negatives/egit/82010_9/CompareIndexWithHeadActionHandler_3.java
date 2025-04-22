		Job job = new Job(UIText.CompareUtils_jobName) {

			@Override
			public IStatus run(IProgressMonitor monitor) {
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				try {
					runCompare(event, repository);
				} catch (Exception e) {
					return Activator.createErrorStatus(
							UIText.CompareWithRefAction_errorOnSynchronize, e);
				}
				return Status.OK_STATUS;
			}

		};
		job.setUser(true);
		job.schedule();
