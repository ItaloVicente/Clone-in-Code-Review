				String.format("Relative overhead of monitoring with stack traces of all threads "
					+ "surpassed threshold for measurement %d of %d. It took %.3fs with a relative "
					+ "increase of %.3f%% (allowed < %.3f%%).",
					i, NUM_ALL_STACKS_MEASUREMENTS, tWork[0] / 1e9, relativeDiffAllThreads * 100,
					maxRelativeIncreaseAllStacksAllowed * 100),
				relativeDiffAllThreads <= maxRelativeIncreaseAllStacksAllowed);
