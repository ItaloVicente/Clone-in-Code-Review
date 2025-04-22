		display.syncExec(new Runnable() {
			@Override
			public void run() {
				shutdownThread(thread);
			}
		});
