		StartupThreading.runWithoutExceptions(new StartupRunnable() {

			@Override
			public void runWithException() {
				((GrabFocus) Tweaklets.get(GrabFocus.KEY)).init(getDisplay());
			}
		});

