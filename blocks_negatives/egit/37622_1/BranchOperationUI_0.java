		ILaunchManager launchManager = DebugPlugin.getDefault()
				.getLaunchManager();
		ILaunch[] launches = launchManager.getLaunches();
		for (ILaunch launch : launches) {
			if (launch.isTerminated())
				continue;
			ISourceLocator locator = launch.getSourceLocator();
			if (locator instanceof ISourceLookupDirector) {
				ISourceLookupDirector director = (ISourceLookupDirector) locator;
				ISourceContainer[] containers = director.getSourceContainers();
				if (isAnyProjectInSourceContainers(containers, projects))
					return launch.getLaunchConfiguration();
			}
