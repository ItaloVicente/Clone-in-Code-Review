		StartupThreading.runWithoutExceptions(new StartupRunnable() {
			@Override
			public void runWithException() {
				activateWorkbenchContext();
			}
		});

		StartupThreading.runWithoutExceptions(new StartupRunnable() {
			@Override
			public void runWithException() {
				Menu appMenu = getAppMenu();
				if (appMenu == null)
					return;

				createApplicationMenu(appMenu);
			}
		});

