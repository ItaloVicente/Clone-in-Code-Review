				String.format("Relative overhead of monitoring surpassed threshold for "
					+ "measurement %d of %d. It took %.3fs with a relative increase of %.3f%% "
					+ "(allowed < %.3f%%).",
					i, NUM_UI_STACK_MEASUREMENTS, tWork[0] / 1e9, relativeDiffOneThread * 100,
					maxRelativeIncreaseOneStackAllowed * 100),
				relativeDiffOneThread <= maxRelativeIncreaseOneStackAllowed);
