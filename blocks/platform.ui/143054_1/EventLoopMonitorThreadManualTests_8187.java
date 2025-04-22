		final Runnable backgroundTaskRunnable = () -> {
			final double dutyCycle = 0.10;

			final double min = 100; // ns
			final double max = 1e9; // ns
			final double skew = 0.1; // the degree to which the values cluster around the mode
			final double bias = -1e5; // bias the mode to approach the min (< 0) vs max (> 0)

			double range = max - min;
			double mid = min + range / 2.0;
			double biasFactor = Math.exp(bias);
			Random rng = new Random();

			while (true) {
				double rv = rng.nextGaussian();
				double runFor = mid + (range * (biasFactor / (biasFactor + Math.exp(-rv / skew)) - 0.5));

				long endTime = System.nanoTime() + (long) runFor;
				do {
					doWork(rng.nextInt(), (int) runFor);
				} while (endTime - System.nanoTime() > 0);

				double sleepScale = Math.abs(rng.nextGaussian() / dutyCycle);
				try {
					if (backgroundJobsDone.await((int) Math.round(runFor * sleepScale),
							TimeUnit.NANOSECONDS)) {
						return;
