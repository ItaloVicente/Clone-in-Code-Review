		StartupThreading.runWithoutExceptions(new StartupRunnable() {
			@Override
			public void runWithException() {
				new ShowKeysListener(Workbench.this, PrefUtil.getInternalPreferenceStore());
			}
		});

