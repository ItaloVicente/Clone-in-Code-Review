		boolean gotFreezeEvents =
		logger.getLoggedEvents().size() == NUM_UI_STACK_MEASUREMENTS + NUM_ALL_STACKS_MEASUREMENTS;

		boolean oneThreadOk = worstRelativeDiffOneThread < maxRelativeIncreaseOneStackAllowed;
		boolean allThreadsOk = worstRelativeDiffAllThreads < maxRelativeIncreaseAllStacksAllowed;

