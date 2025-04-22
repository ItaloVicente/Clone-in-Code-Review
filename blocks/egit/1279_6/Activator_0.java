		setupFocusHandling();
	}

	static boolean isActive() {
		final AtomicBoolean ret = new AtomicBoolean();
		final Display display = PlatformUI.getWorkbench().getDisplay();
		display.syncExec(new Runnable() {
			public void run() {
				ret.set(display.getActiveShell() != null);
			}
		});
		return ret.get();
	}

	private void setupFocusHandling() {
		focusListener = new IWindowListener() {

			public void windowOpened(IWorkbenchWindow window) {
			}

			public void windowDeactivated(IWorkbenchWindow window) {
			}

			public void windowClosed(IWorkbenchWindow window) {
			}

			public void windowActivated(IWorkbenchWindow window) {
				if (rcs.doReschedule)
					rcs.schedule();
				refreshJob.schedule();
			}
		};
		PlatformUI.getWorkbench().addWindowListener(focusListener);
