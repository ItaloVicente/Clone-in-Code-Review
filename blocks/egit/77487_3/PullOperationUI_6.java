		execute(monitor, true);
	}

	private void execute(IProgressMonitor monitor, boolean launchCheck) {
		SubMonitor progress = SubMonitor.convert(monitor,
				launchCheck ? 11 : 10);
		if (launchCheck && LaunchFinder.shouldCancelBecauseOfRunningLaunches(
				Arrays.asList(repositories), progress.newChild(1))) {
			return;
		}
