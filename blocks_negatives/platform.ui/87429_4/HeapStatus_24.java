        getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (!isDisposed()) {
					getDisplay().timerExec(updateInterval, timer);
				}
