	public void execute(IProgressMonitor m) throws CoreException {
		IWorkspaceRunnable action = new IWorkspaceRunnable() {
			@Override
			public void run(IProgressMonitor pm) throws CoreException {
				SubMonitor progress = SubMonitor.convert(pm,2);
				progress.subTask(MessageFormat.format(CoreText.RewordCommitOperation_rewording,
						commit.name()));
