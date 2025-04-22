				final FindToolbarThread finder = createFinder();
				getDisplay().timerExec(200, new Runnable() {
					@Override
					public void run() {
						finder.start();
					}
				});
