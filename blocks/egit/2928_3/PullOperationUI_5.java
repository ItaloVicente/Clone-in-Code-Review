			jobName = NLS.bind(UIText.PullOperationUI_PullingTaskName,
					shortBranchName, repoName);
		} else
			jobName = UIText.PullOperationUI_PullingMultipleTaskName;
		Job job = new Job(jobName) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				execute(monitor);
				return Status.OK_STATUS;
