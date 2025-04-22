		display.timerExec(DELAY, new Runnable() {
			@Override
			public void run() {
				if ((System.currentTimeMillis() > (myStartTime - DELAY))
						&& (startTime == myStartTime)) {
					Collection<Binding> partialMatches = bindingService.getPartialMatches(sequence);
					openKeyAssistShell(partialMatches);
				}
