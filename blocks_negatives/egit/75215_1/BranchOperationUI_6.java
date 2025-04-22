
		if (shouldCancelBecauseOfRunningLaunches(new NullProgressMonitor()))
			return;

		askForTargetIfNecessary();
		if (target == null)
			return;

