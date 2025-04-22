			long elapsedNanos = System.nanoTime() - lastRunNanos;
			long elapsedMillis = elapsedNanos / 1_000_000;
			if (elapsedMillis > minWaitBetweenRunMillis) {
				runner.run();
			} else if (!display.isDisposed()) {
				long milisDifference = minWaitBetweenRunMillis - elapsedMillis;
				int milisToWait = Math.max((int) milisDifference, minWaitBetweenRunMillis);
				display.timerExec(milisToWait, runner);
			} else {
				scheduled.set(false);
