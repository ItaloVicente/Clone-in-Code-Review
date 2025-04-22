				if (deadlockTimerStart != 0) {
					long totalDuration = currTime - deadlockTimerStart;
					if (totalDuration >= deadlockThreshold) {
						if (numSamples > maxLoggedStackSamples) {
							decimate(stackSamples, numSamples, maxLoggedStackSamples);
							numSamples = maxLoggedStackSamples;
						}
						if (uiThreadFilter.shouldLogEvent(stackSamples, numSamples, uiThreadId)) {
							logEvent(new UiFreezeEvent(deadlockTimerStart, totalDuration,
									Arrays.copyOf(stackSamples, numSamples),
									true, starvedAwake, starvedAsleep));
							deadlockTimerStart = 0; // Don't log potential deadlock more than once.
						}
					}
				}
