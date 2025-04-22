	void forceActiveShell(Shell shell) {
		IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
		for (IWorkbenchWindow w : windows) {
			w.getShell().setMinimized(true);
			processEvents();
		}
		for (IWorkbenchWindow w : windows) {
			w.getShell().setMinimized(false);
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
	}

	static class MyShellListener implements ShellListener {
		private AtomicBoolean shellIsActive;

		public MyShellListener(AtomicBoolean shellIsActive) {
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

