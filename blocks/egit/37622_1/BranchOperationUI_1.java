	private static class LaunchConfigJob extends Job {

		private Set<IProject> projects;

		private ILaunchConfiguration launchConfig;

		LaunchConfigJob(Set<IProject> projects) {
			super(UIText.BranchOperationUI_SearchRunningLaunchConfiguration);
			this.projects = projects;
		}

		ILaunchConfiguration getRunningLaunchConfiguration() {
			return launchConfig;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			ILaunchManager launchManager = DebugPlugin.getDefault()
					.getLaunchManager();
			ILaunch[] launches = launchManager.getLaunches();
			for (ILaunch launch : launches) {
				if (monitor.isCanceled())
					return Status.CANCEL_STATUS;
				if (launch.isTerminated())
					continue;
				ISourceLocator locator = launch.getSourceLocator();
				if (locator instanceof ISourceLookupDirector) {
					ISourceLookupDirector director = (ISourceLookupDirector) locator;
					ISourceContainer[] containers = director
							.getSourceContainers();
					if (isAnyProjectInSourceContainers(containers, projects)) {
						launchConfig = launch.getLaunchConfiguration();
						return Status.OK_STATUS;
					}
				}
