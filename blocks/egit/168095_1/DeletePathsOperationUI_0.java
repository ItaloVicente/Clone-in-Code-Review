		JobUtil.scheduleUserWorkspaceJob(new IEGitOperation() {

			@Override
			public ISchedulingRule getSchedulingRule() {
				return null;
			}

			@Override
			public void execute(IProgressMonitor monitor) throws CoreException {
				DeletePathsOperation op = new DeletePathsOperation(paths);
				op.execute(monitor);
			}

		}, UIText.DeletePathsOperationUI_DeleteFilesJobName,
