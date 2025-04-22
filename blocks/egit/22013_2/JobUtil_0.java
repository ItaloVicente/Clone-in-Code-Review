
	public static void scheduleUserWorkspaceJob(final IEGitOperation op,
			String jobName, final Object jobFamily) {
		scheduleUserWorkspaceJob(op, jobName, jobFamily, null);
	}

	public static void scheduleUserWorkspaceJob(final IEGitOperation op,
			String jobName, final Object jobFamily,
			IJobChangeListener jobChangeListener) {
		Job job = new WorkspaceJob(jobName) {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				try {
					op.execute(monitor);
				} catch (CoreException e) {
					return e.getStatus();
				}
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				if (jobFamily != null && family.equals(jobFamily))
					return true;
				return super.belongsTo(family);
			}
		};
		job.setRule(op.getSchedulingRule());
		job.setUser(true);
		if (jobChangeListener != null)
			job.addJobChangeListener(jobChangeListener);
		job.schedule();
	}
