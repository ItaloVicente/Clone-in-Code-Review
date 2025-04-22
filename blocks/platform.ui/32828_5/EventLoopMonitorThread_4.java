			if (!haveAlreadyLoggedPossibleDeadlock && lastActive > 0 &&
					totalDuration > deadlockThreshold &&
					filterHandler.shouldLogEvent(stackSamples, numStacks, uiThreadId)) {
				stackSamples = Arrays.copyOf(stackSamples, numStacks);
				logEvent(new UiFreezeEvent(lastActive, totalDuration,
						Arrays.copyOf(stackSamples, numStacks), true));
