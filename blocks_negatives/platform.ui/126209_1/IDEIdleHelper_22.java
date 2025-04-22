		idleListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				display.timerExec(IDLE_INTERVAL, handler);
			}
		};
