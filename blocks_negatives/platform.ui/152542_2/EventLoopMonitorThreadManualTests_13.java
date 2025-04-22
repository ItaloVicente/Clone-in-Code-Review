		display.syncExec(new Runnable() {
			@Override
			public void run() {
				monitorThread.start();

				display.disposeExec(new Runnable() {
					@Override
					public void run() {
						shutdownThread(monitorThread);
					}
				});
			}
