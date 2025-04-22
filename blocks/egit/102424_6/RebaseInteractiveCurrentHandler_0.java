		}
		if (LaunchFinder.shouldCancelBecauseOfRunningLaunches(repository,
				null)) {
			return null;
		}
		super.execute(event);
