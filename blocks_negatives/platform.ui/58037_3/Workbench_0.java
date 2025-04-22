	/*
	 * If a perspective was specified on the command line (-perspective) then
	 * force that perspective to open in the active window.
	 */
	void forceOpenPerspective() {
		if (getWorkbenchWindowCount() == 0) {
			return;
		}

		String perspId = null;
		String[] commandLineArgs = Platform.getCommandLineArgs();
		for (int i = 0; i < commandLineArgs.length - 1; i++) {
				perspId = commandLineArgs[i + 1];
				break;
			}
		}
		if (perspId == null) {
			return;
		}
		IPerspectiveDescriptor desc = getPerspectiveRegistry().findPerspectiveWithId(perspId);
		if (desc == null) {
			return;
		}

		IWorkbenchWindow win = getActiveWorkbenchWindow();
		if (win == null) {
			win = getWorkbenchWindows()[0];
		}

		final String threadPerspId = perspId;
		final IWorkbenchWindow threadWin = win;
		StartupThreading.runWithoutExceptions(new StartupRunnable() {
			@Override
			public void runWithException() throws Throwable {
				try {
					showPerspective(threadPerspId, threadWin);
				} catch (WorkbenchException e) {
					WorkbenchPlugin.log(msg, new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, 0,
							msg, e));
				}
			}
		});
	}

