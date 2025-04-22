		final Runnable backgroundTaskRunnable = new Runnable() {
			@Override
			public void run() {
				final double dutyCycle = 0.10;


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
						}
					} catch (InterruptedException e) {
