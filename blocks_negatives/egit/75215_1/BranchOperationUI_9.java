
		if (shouldCancelBecauseOfRunningLaunches(monitor))
			return;

		askForTargetIfNecessary();
		if (target == null)
			return;

		BranchOperation bop = new BranchOperation(repository, target);
		bop.execute(monitor);

