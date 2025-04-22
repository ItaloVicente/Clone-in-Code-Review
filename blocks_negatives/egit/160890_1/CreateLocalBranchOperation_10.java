		IWorkspaceRunnable action = new IWorkspaceRunnable() {
			@Override
			public void run(IProgressMonitor actMonitor) throws CoreException {
				String taskName = NLS.bind(
						CoreText.CreateLocalBranchOperation_CreatingBranchMessage,
						name);
				SubMonitor progress = SubMonitor.convert(actMonitor);
				progress.setTaskName(taskName);
				try (Git git = new Git(repository)) {
					if (ref != null) {
						SetupUpstreamMode mode;
						if (upstreamConfig == null)
							mode = SetupUpstreamMode.NOTRACK;
						else
							mode = SetupUpstreamMode.SET_UPSTREAM;
						git.branchCreate().setName(name).setStartPoint(
								ref.getName()).setUpstreamMode(mode).call();
					}
