		Job job = new Job(jobName) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					new ResetOperation(repo, commit.getName(), resetType)
							.execute(null);
				} catch (CoreException e) {
					return e.getStatus();
				}
				return Status.OK_STATUS;
			}
		};
		job.schedule();
