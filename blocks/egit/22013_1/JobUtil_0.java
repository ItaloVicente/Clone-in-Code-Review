			final Object jobFamily, boolean batchResourceChangeEvents,
			IJobChangeListener jobChangeListener) {
		Job job = null;
		if (batchResourceChangeEvents) {
			job = new WorkspaceJob(jobName) {
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
		} else {
			job = new Job(jobName) {
				@Override
				public IStatus run(IProgressMonitor monitor) {
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
