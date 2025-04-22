		final Runnable wakeDisplay = new Runnable() {

			@Override
			public void run() {
				if (!display.isDisposed()) {
					display.wake();
				}
			}
		};
