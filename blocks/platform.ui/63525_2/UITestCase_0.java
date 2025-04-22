	public static void waitForJobs(long minTimeMs, long maxTimeMs) {
		if (maxTimeMs < minTimeMs) {
			throw new IllegalArgumentException("Max time is smaller as min time!");
		}
		final long start = System.currentTimeMillis();
		while (System.currentTimeMillis() - start < minTimeMs) {
			processEvents();
			sleep(10);
		}
		while (!Job.getJobManager().isIdle() && System.currentTimeMillis() - start < maxTimeMs) {
			processEvents();
			sleep(10);
		}
	}

	protected static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			return;
		}
	}

	protected boolean forceActive(Shell shell) {
		Display display = PlatformUI.getWorkbench().getDisplay();
		Shell[] shells = display.getShells();
		for (Shell s : shells) {
			s.setMinimized(true);
			processEvents();
		}
		for (Shell s : shells) {
			s.setMinimized(false);
			processEvents();
		}
		shell.setVisible(false);
		processEvents();
		shell.setMinimized(true);
		processEvents();
		shell.setVisible(true);
		processEvents();
		shell.setMinimized(false);
		processEvents();
		shell.forceActive();
		processEvents();
		shell.forceFocus();
		processEvents();
		return display.getActiveShell() == shell;
	}

	public static class ShellStateListener implements ShellListener {
		private AtomicBoolean shellIsActive;

		public ShellStateListener(AtomicBoolean shellIsActive) {
			this.shellIsActive = shellIsActive;
		}

		@Override
		public void shellIconified(ShellEvent e) {
			shellIsActive.set(false);
		}

		@Override
		public void shellDeiconified(ShellEvent e) {
			shellIsActive.set(true);
		}

		@Override
		public void shellDeactivated(ShellEvent e) {
			shellIsActive.set(false);
		}

		@Override
		public void shellClosed(ShellEvent e) {
			shellIsActive.set(false);
		}

		@Override
		public void shellActivated(ShellEvent e) {
			shellIsActive.set(true);
		}
	}

