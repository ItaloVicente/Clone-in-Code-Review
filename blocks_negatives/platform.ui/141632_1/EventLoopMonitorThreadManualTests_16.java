		final Runnable doFixedAmountOfWork = new Runnable() {
			@Override
			public void run() {
				long start = System.nanoTime();
				long result = doWork(start, numIterations);
				tWork[0] = System.nanoTime() - start;
				workOutput[0] ^= result;
			}
