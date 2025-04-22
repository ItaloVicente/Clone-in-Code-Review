		display.syncExec(new Runnable() {
			@Override
			public void run() {
				BusyIndicator.showWhile(display, runnableWithStatus);
			}
		});
