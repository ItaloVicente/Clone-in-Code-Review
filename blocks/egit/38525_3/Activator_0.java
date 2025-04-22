	private static final class EGitShellListener extends ShellAdapter {

		private Shell shell;

		EGitShellListener(Shell shell) {
			Assert.isNotNull(shell);
			this.shell = shell;
		}

		public void shellActivated(ShellEvent e) {
			plugin.isActive.set(true);
		}

		public void shellClosed(ShellEvent e) {
			shell.removeShellListener(this);
		}

		public void shellDeactivated(ShellEvent e) {
			plugin.isActive.set(false);
		}
	}

	private void registerShellListeners() {
		final Display display = PlatformUI.getWorkbench().getDisplay();
		if (display.isDisposed())
			return;
		display.asyncExec(new Runnable() {
			public void run() {
				Shell[] shells = display.getShells();
				for (Shell shell : shells) {
					if (shell != null)
						shell.addShellListener(new EGitShellListener(shell));
				}
			}
		});
	}

