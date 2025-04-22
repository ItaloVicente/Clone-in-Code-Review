			Runnable runner = () -> {
				scheduled.set(false);
				runnable.run();
				lastRunNanos = System.nanoTime();
			};
			if (System.nanoTime() - lastRunNanos > minNanos) {
				runner.run(); // run immediately
			} else if (!display.isDisposed()) { // wait
				display.timerExec(minWaitBetweenRunMillis, runner);
			} else { // fail
				scheduled.set(false);
